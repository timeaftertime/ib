package cn.milai.ib.container.lifecycle;

import java.util.List;

import com.google.common.collect.Lists;

import cn.milai.common.base.Collects;
import cn.milai.ib.container.BaseCloseableContainer;
import cn.milai.ib.container.ContainerClosedException;
import cn.milai.ib.container.listener.ContainerListener;

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
	private boolean started = false;

	/**
	 * 游戏对象是否被固定住
	 */
	private volatile boolean pined;

	/**
	 * 容器生命周期事件监听器列表
	 */
	private List<LifecycleListener> lifecycleListeners;

	private LifecycleThread refresher = new LifecycleThread(this);

	public BaseLifecycleContainer() {
		pined = false;
		lifecycleListeners = Lists.newArrayList();
	}

	@Override
	public long getFrame() { return refresher.getFrame(); }

	@Override
	public int getEpoch() { return epoch; }

	@Override
	public final void reset() {
		super.reset();
		epoch++;
		synchronized (lifecycleListeners) {
			notifyEpochChanged();
			lifecycleListeners = Collects.unfilter(lifecycleListeners, ContainerListener::inEpoch);
		}
		pined = false;
		// 确保重置后处于非暂停状态
		refresher.cancelPause();
	}

	@Override
	public synchronized final void start() throws ContainerClosedException {
		checkClosed();
		if (started) {
			return;
		}
		started = true;
		refresher.start();
	}

	@Override
	public synchronized final void close() {
		super.close();
		for (LifecycleListener listener : safeLifecycleListeners()) {
			listener.onContainerClosed(this);
		}
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
			listener.onContainerClosed(this);
			return;
		}
		synchronized (lifecycleListeners) {
			this.lifecycleListeners.add(listener);
		}
	}

	@Override
	public void removeLifecycleListener(LifecycleListener listener) {
		synchronized (lifecycleListeners) {
			this.lifecycleListeners.remove(listener);
		}
	}

	private void notifyEpochChanged() {
		for (LifecycleListener listener : safeLifecycleListeners()) {
			listener.afterEpochChanged(this);
		}
	}

	/**
	 * 通知刷新监听器
	 */
	private void notifyAfterRefresh() {
		for (LifecycleListener listener : safeLifecycleListeners()) {
			listener.afterRefresh(this);
		}
	}

	/**
	 * 线程安全地获取 {@link #lifecycleListeners} 的副本
	 * @return
	 */
	private List<LifecycleListener> safeLifecycleListeners() {
		synchronized (lifecycleListeners) {
			return Lists.newArrayList(lifecycleListeners);
		}
	}

	@Override
	public boolean isPined() { return pined; }

	/**
	 * 进行实际的刷新动作
	 */
	protected void doRefresh() {
		notifyAfterRefresh();
	}

}
