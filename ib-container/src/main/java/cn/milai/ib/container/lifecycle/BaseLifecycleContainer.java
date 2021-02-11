package cn.milai.ib.container.lifecycle;

import java.util.List;

import com.google.common.collect.Lists;

import cn.milai.common.base.Collects;
import cn.milai.ib.IBObject;
import cn.milai.ib.container.BaseCloseableIBContainer;
import cn.milai.ib.container.ContainerClosedException;

/**
 * Container 的抽象基类
 * @author milai
 * @date 2020.03.25
 */
public class BaseLifecycleContainer extends BaseCloseableIBContainer implements LifecycleContainer {

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
	 * 容器事件监听器列表
	 */
	private List<ContainerEventListener> containerEventListeners;

	private LifecycleThread refresher = new LifecycleThread(this);

	public BaseLifecycleContainer() {
		pined = false;
		containerEventListeners = Lists.newArrayList();
	}

	@Override
	public long getFrame() { return refresher.getFrame(); }

	@Override
	public int getEpoch() { return epoch; }

	@Override
	public final void reset() {
		super.reset();
		synchronized (containerEventListeners) {
			epoch++;
			notifyEpochChanged();
			containerEventListeners = Collects.filter(containerEventListeners, ContainerEventListener::acrossEpoch);
		}
		pined = false;
		// 确保重置后处于非暂停状态
		refresher.cancelPause();
	}

	@Override
	public final boolean addObject(IBObject obj) throws ContainerClosedException {
		if (super.addObject(obj)) {
			notifyOnObjectAdded(obj);
			return true;
		}
		return false;
	}

	@Override
	public final boolean removeObject(IBObject obj) throws ContainerClosedException {
		if (super.removeObject(obj)) {
			notifyOnObjectRemoved(obj);
			return true;
		}
		return false;
	}

	@Override
	public synchronized final void start() {
		checkClosed();
		if (started) {
			return;
		}
		started = true;
		refresher.start();
	}

	@Override
	public synchronized final void close() {
		checkClosed();
		super.close();
		for (ContainerEventListener listener : syncContainerEventListeners()) {
			listener.onContainerClosed();
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
	public void addEventListener(ContainerEventListener listener) {
		if (isClosed()) {
			listener.onContainerClosed();
			return;
		}
		synchronized (containerEventListeners) {
			this.containerEventListeners.add(listener);
		}
	}

	@Override
	public void removeEventListener(ContainerEventListener listener) {
		synchronized (containerEventListeners) {
			this.containerEventListeners.remove(listener);
		}
	}

	private void notifyEpochChanged() {
		for (ContainerEventListener listener : syncContainerEventListeners()) {
			listener.afterEpochChanged(this);
		}
	}

	private void notifyOnObjectAdded(IBObject obj) {
		for (ContainerEventListener listener : syncContainerEventListeners()) {
			listener.onObjectAdded(obj);
		}
	}

	private void notifyOnObjectRemoved(IBObject obj) {
		for (ContainerEventListener listener : syncContainerEventListeners()) {
			listener.onObjectRemoved(obj);
		}
	}

	/**
	 * 通知刷新监听器
	 */
	private void notifyAfterRefresh() {
		for (ContainerEventListener listener : syncContainerEventListeners()) {
			listener.afterRefresh(this);
		}
	}

	/**
	 * 同步获取 {@link ContainerEventListener} 的副本
	 * @return
	 */
	private List<ContainerEventListener> syncContainerEventListeners() {
		synchronized (containerEventListeners) {
			return Lists.newArrayList(containerEventListeners);
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
