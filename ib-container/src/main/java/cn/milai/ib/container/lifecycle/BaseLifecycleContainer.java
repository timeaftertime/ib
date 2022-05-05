package cn.milai.ib.container.lifecycle;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import cn.milai.beginning.collection.Filter;
import cn.milai.ib.conf.IBConf;
import cn.milai.ib.container.BaseCloseableContainer;
import cn.milai.ib.container.ContainerClosedException;
import cn.milai.ib.container.listener.ContainerListener;
import cn.milai.ib.container.listener.LifecycleListener;

/**
 * Container 的抽象基类
 * @author milai
 * @date 2020.03.25
 */
public class BaseLifecycleContainer extends BaseCloseableContainer implements LifecycleContainer {

	/**
	 * 纪元，即调用 {@link #reset()} 的次数
	 */
	private int epoch = 0;

	/**
	 * 帧数，即容器启动到现在刷新的次数
	 */
	private volatile long frame = 0;

	/**
	 * 容器是否已经启动
	 */
	private AtomicBoolean started = new AtomicBoolean();

	/**
	 * 是否正在运行
	 */
	private boolean running = false;

	/**
	 * 是否被暂停
	 */
	private boolean paused = false;

	/**
	 * 游戏对象是否被固定住
	 */
	private volatile boolean pined = false;

	/**
	 * 上次开始刷新的毫秒数
	 */
	private long lastStartMillisec;

	/**
	 * 容器生命周期事件监听器列表
	 */
	private List<LifecycleListener> lifecycleListeners = new CopyOnWriteArrayList<>();

	private EventLoop eventLoop;

	private Unsafe unsafe = new BaseUnsafe();

	@Override
	public long getFrame() { return frame; }

	@Override
	public int getEpoch() { return epoch; }

	@Override
	public final void reset() {
		super.reset();
		epoch++;
		notifyEpochChanged();
		lifecycleListeners = Filter.remove(lifecycleListeners, ContainerListener::inEpoch);
		pined = false;
		paused = false;
	}

	@Override
	public final void start() throws ContainerClosedException {
		if (!started.compareAndSet(false, true)) {
			return;
		}
		for (LifecycleListener listener : lifecycleListeners) {
			listener.onStart(this);
		}
		running = true;
	}

	@Override
	public void refresh() {
		if (!isRunning()) {
			return;
		}
		lastStartMillisec = System.currentTimeMillis();
		frame++;
		notifyAfterRefresh();
	}

	@Override
	public final boolean close() {
		if (super.close()) {
			running = false;
			eventLoop().unregister(this);
			for (LifecycleListener listener : lifecycleListeners) {
				listener.onClosed(this);
			}
			return true;
		}
		return false;
	}

	@Override
	public final void switchPause() {
		if (paused) {
			paused = false;
		} else {
			paused = true;
		}
	}

	@Override
	public final void setPined(boolean pined) throws ContainerClosedException { this.pined = pined; }

	@Override
	public boolean isPaused() { return paused; }

	@Override
	public void addLifecycleListener(LifecycleListener listener) {
		if (isClosed()) {
			listener.onClosed(this);
			return;
		}
		if (isRunning()) {
			listener.onStart(this);
		}
		this.lifecycleListeners.add(listener);
	}

	@Override
	public void removeLifecycleListener(LifecycleListener listener) {
		this.lifecycleListeners.remove(listener);
	}

	private void notifyEpochChanged() {
		for (LifecycleListener listener : lifecycleListeners) {
			listener.onEpochChanged(this);
		}
	}

	/**
	 * 通知刷新监听器
	 */
	private void notifyAfterRefresh() {
		for (LifecycleListener listener : lifecycleListeners) {
			listener.onRefresh(this);
		}
	}

	@Override
	public Unsafe unsafe() {
		return unsafe;
	}

	@Override
	public boolean isPined() { return pined; }

	@Override
	public boolean isRunning() { return running; }

	@Override
	public EventLoop eventLoop() {
		return eventLoop;
	}

	@Override
	public long nextRefreshTime() {
		return lastStartMillisec + IBConf.refreshMillisec();
	}

	private class BaseUnsafe implements Unsafe {
		@Override
		public void register(EventLoop eventLoop) {
			if (BaseLifecycleContainer.this.eventLoop != null) {
				throw new IllegalStateException("已绑定 EventLoop: " + eventLoop);
			}
			start();
			BaseLifecycleContainer.this.eventLoop = eventLoop;
		}

		@Override
		public void unregister() {
			BaseLifecycleContainer.this.eventLoop = null;
		}
	}
}
