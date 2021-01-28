package cn.milai.ib.container;

import java.util.List;

import com.google.common.collect.Lists;

import cn.milai.ib.IBObject;

/**
 * Container 的抽象基类
 * @author milai
 * @date 2020.03.25
 */
public abstract class AbstractContainer implements Container {

	/**
	 * 纪元，即调用 {@link #reset()} 的次数
	 */
	private int epoch = 0;

	/**
	 * 帧数，即容器启动到现在刷新的次数
	 */
	private volatile long frame = 0;

	/**
	 * 容器中所有游戏对象
	 */
	private List<IBObject> objs;

	/**
	 * 容器事件监听器列表
	 */
	private List<ContainerEventListener> containerEventListeners;

	public AbstractContainer() {
		objs = Lists.newArrayList();
		containerEventListeners = Lists.newArrayList();
	}

	@Override
	public long getFrame() {
		return frame;
	}

	@Override
	public int getEpoch() {
		return epoch;
	}

	@Override
	public final void reset() {
		synchronized (objs) {
			objs.clear();
		}
		synchronized (containerEventListeners) {
			epoch++;
			notifyEpochChanged();
			containerEventListeners.clear();
		}
		resetContainer();
	}

	@Override
	public void addObject(IBObject obj) throws IBContainerException {
		synchronized (objs) {
			this.objs.add(obj);
			notifyOnObjectAdded(obj);
		}
	}

	@Override
	public void removeObject(IBObject obj) throws IBContainerException {
		synchronized (objs) {
			this.objs.remove(obj);
			notifyOnObjectRemoved(obj);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getAll(Class<T> type) throws IBContainerException {
		List<IBObject> allObjs;
		synchronized (objs) {
			allObjs = Lists.newArrayList(objs);
		}
		if (type == IBObject.class) {
			return (List<T>) allObjs;
		}
		List<T> allOfType = Lists.newArrayList();
		for (IBObject o : allObjs) {
			if (type.isInstance(o))
				allOfType.add((T) o);
		}
		return allOfType;
	}

	@Override
	public void addEventListener(ContainerEventListener listener) {
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
	protected void notifyAfterRefresh() {
		frame++;
		for (ContainerEventListener listener : syncContainerEventListeners()) {
			listener.afterRefresh(this);
		}
	}

	/**
	 * 同步获取 ContainerEventListener 的副本
	 * @return
	 */
	private List<ContainerEventListener> syncContainerEventListeners() {
		synchronized (containerEventListeners) {
			return Lists.newArrayList(containerEventListeners);
		}
	}

	/**
	 * 重置容器，在 {@link Container#reset()} 方法被调用后调用
	 */
	protected abstract void resetContainer();

}
