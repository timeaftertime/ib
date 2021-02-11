package cn.milai.ib.container.lifecycle;

import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.container.ContainerClosedException;

/**
 * {@link LifecycleContainer} 容器的刷新线程
 * @author milai
 * @date 2020.03.25
 */
public class LifecycleThread extends Thread {

	/**
	 * 每帧的实际时间间隔
	 */
	private static final long MILLISEC_PER_FRAME = SystemConf.refreshMillisec();

	/**
	 * 帧数，即容器启动到现在刷新的次数
	 */
	private volatile long frame = 0;

	private static final String THREAD_NAME = "IBLifecycle";

	/**
	 * 是否被暂停
	 */
	private volatile boolean paused = false;

	private BaseLifecycleContainer container;

	LifecycleThread(LifecycleContainer container) {
		this.container = (BaseLifecycleContainer) container;
	}

	@Override
	public void run() {
		try {
			setName(THREAD_NAME);
			while (!container.isClosed()) {
				responsePaused();
				frame++;
				container.doRefresh();
				sleepOneFrame();
			}
		} catch (ContainerClosedException e) {
			// 容器关闭，停止刷新线程
		}
	}

	private void responsePaused() {
		if (isPaused()) {
			while (!Thread.interrupted() && !container.isClosed()) {
				sleepOneFrame();
			}
		} else {
			// 确保非 paused 状态清除中断状态，以免 sleepOneFrame 失效
			Thread.interrupted();
		}
	}

	private void sleepOneFrame() {
		try {
			Thread.sleep(MILLISEC_PER_FRAME);
		} catch (InterruptedException e) {
			// 保留中断状态
			this.interrupt();
		}
	}

	/**
	 * 是否处于 pause 状态
	 * @return
	 */
	boolean isPaused() { return paused; }

	/**
	 * 切换（开启/取消）暂停状态
	 */
	void switchPause() {
		if (paused) {
			cancelPause();
		} else {
			paused = true;
		}
	}

	/**
	 * 取消暂停状态
	 */
	void cancelPause() {
		paused = false;
		this.interrupt();
	}

	public long getFrame() { return frame; }
}
