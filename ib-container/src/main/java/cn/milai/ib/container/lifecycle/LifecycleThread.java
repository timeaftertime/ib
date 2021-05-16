package cn.milai.ib.container.lifecycle;

import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.milai.common.thread.BlockCondition;
import cn.milai.ib.conf.IBConf;
import cn.milai.ib.container.ContainerClosedException;
import cn.milai.ib.container.listener.LifecycleListener;

/**
 * {@link LifecycleContainer} 容器的刷新线程
 * @author milai
 * @date 2020.03.25
 */
public class LifecycleThread extends Thread {

	private static final Logger LOG = LoggerFactory.getLogger(LifecycleThread.class);

	/**
	 * 帧数，即容器启动到现在刷新的次数
	 */
	private volatile long frame = 0;

	private static final String THREAD_NAME = "IBLifecycle";

	/**
	 * 是否被暂停
	 */
	private AtomicBoolean paused = new AtomicBoolean();;

	private BlockCondition pauseCondition;

	private BaseLifecycleContainer container;

	private long lastStartMillisec;

	LifecycleThread(LifecycleContainer container) {
		this.container = (BaseLifecycleContainer) container;
	}

	@Override
	public void run() {
		try {
			setName(THREAD_NAME);
			while (!container.isClosed()) {
				startNewFrame();
				frame++;
				container.doRefresh();
				sleepOneFrame();
				if (isPaused()) {
					LOG.debug("容器开始暂停……");
					pauseCondition.await();
					LOG.debug("容器结束暂停……");
				}
			}
		} catch (ContainerClosedException e) {
			// 容器关闭，停止刷新线程
		}
	}

	private void sleepOneFrame() {
		long refreshMillisec = IBConf.refreshMillisec();
		long pre = System.currentTimeMillis() - lastStartMillisec;
		if (pre > refreshMillisec) {
			LOG.warn("刷新超时: expected = {}, real = {}", refreshMillisec, pre);
			return;
		}
		try {
			Thread.sleep(refreshMillisec - pre);
		} catch (InterruptedException e) {
			LOG.warn("刷新等待被中断");
		}
	}

	private void startNewFrame() {
		lastStartMillisec = System.currentTimeMillis();
	}

	/**
	 * 是否处于 pause 状态
	 * @return
	 */
	boolean isPaused() { return paused.get(); }

	/**
	 * 切换（开启/取消）暂停状态
	 */
	synchronized void switchPause() {
		if (paused.compareAndSet(false, true)) {
			pauseCondition = new BlockCondition(() -> !paused.get(), c -> paused.set(false));
			container.addLifecycleListener(new LifecycleListener() {
				@Override
				public void onClosed(LifecycleContainer container) {
					if (pauseCondition != null && !pauseCondition.isMet()) {
						pauseCondition.toMet();
					}
				}
			});
		} else {
			cancelPause();
		}
	}

	/**
	 * 取消暂停状态
	 */
	synchronized void cancelPause() {
		if (paused.compareAndSet(true, false)) {
			pauseCondition.toMet();
		}
	}

	public long getFrame() { return frame; }
}
