package cn.milai.ib.container.lifecycle;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import cn.milai.beginning.collection.Filter;
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
	 * 容器是否已经启动
	 */
	private AtomicBoolean started = new AtomicBoolean();

	/**
	 * 游戏对象是否被固定住
	 */
	private volatile boolean pined = false;

	/**
	 * 容器生命周期事件监听器列表
	 */
	private List<LifecycleListener> lifecycleListeners = new CopyOnWriteArrayList<>();

	private LifecycleThread refresher = new LifecycleThread(this);

	@Override
	public long getFrame() { return refresher.getFrame(); }

	@Override
	public int getEpoch() { return epoch; }

	@Override
	public final void reset() {
		super.reset();
		epoch++;
		notifyEpochChanged();
		lifecycleListeners = Filter.remove(lifecycleListeners, ContainerListener::inEpoch);
		pined = false;
		// 确保重置后处于非暂停状态
		refresher.cancelPause();
	}

	@Override
	public final void start() throws ContainerClosedException {
		checkClosed();
		if (!started.compareAndSet(false, true)) {
			return;
		}
		refresher.start();
	}

	@Override
	public final boolean close() {
		if (super.close()) {
			for (LifecycleListener listener : lifecycleListeners) {
				listener.onClosed(this);
			}
			return true;
		}
		return false;
	}

	@Override
	public final void switchPause() {
		checkClosed();
		refresher.switchPause();
	}

	@Override
	public final void setPined(boolean pined) throws ContainerClosedException {
		checkClosed();
		this.pined = pined;
	}

	@Override
	public boolean isPaused() { return refresher.isPaused(); }

	@Override
	public void addLifecycleListener(LifecycleListener listener) {
		if (isClosed()) {
			listener.onClosed(this);
			return;
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
	public boolean isPined() { return pined; }

	@Override
	public boolean isRunning() { return started.get() && !isClosed(); }

	/**
	 * 进行实际的刷新动作
	 */
	protected void doRefresh() {
		notifyAfterRefresh();
	}

}
