package cn.milai.ib.container;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import cn.milai.common.base.Collects;
import cn.milai.ib.IBObject;
import cn.milai.ib.container.listener.ContainerListener;
import cn.milai.ib.container.listener.ObjectListener;

/**
 * {@link Container} 默认实现
 * @author milai
 * @date 2021.02.06
 */
public class BaseContainer implements Container {

	private int w;
	private int h;

	private Set<IBObject> objs = Sets.newHashSet();

	/**
	 * 容器对象事件监听器列表
	 */
	private List<ObjectListener> objectListeners = Lists.newArrayList();

	@Override
	public synchronized boolean addObject(IBObject obj) {
		if (objs.add(obj)) {
			notifyObjectAdded(obj);
			return true;
		}
		return false;
	}

	@Override
	public synchronized boolean removeObject(IBObject obj) {
		if (objs.remove(obj)) {
			notifyObjectRemoved(obj);
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IBObject> List<T> getAll(Class<T> type) {
		List<T> allOfType = Lists.newArrayList();
		if (type == IBObject.class) {
			return (List<T>) Lists.newArrayList(objs);
		}
		for (IBObject o : Lists.newArrayList(objs)) {
			if (type.isInstance(o))
				allOfType.add((T) o);
		}
		return allOfType;
	}

	@Override
	public void reset() {
		objs.clear();
		synchronized (objectListeners) {
			notifyObjectCleared();
			objectListeners = Collects.unfilter(objectListeners, ContainerListener::inEpoch);
		}
	}

	@Override
	public void addObjectListener(ObjectListener listener) {
		synchronized (objectListeners) {
			objectListeners.add(listener);
		}
	}

	@Override
	public void removeObjectListener(ObjectListener listener) {
		synchronized (objectListeners) {
			objectListeners.remove(listener);
		}
	}

	private void notifyObjectRemoved(IBObject obj) {
		notifyObjectsRemoved(Collections.unmodifiableList(Arrays.asList(obj)));
	}

	private void notifyObjectCleared() {
		notifyObjectsRemoved(Collections.unmodifiableList(getAll(IBObject.class)));
	}

	private void notifyObjectsRemoved(List<IBObject> all) {
		for (ObjectListener listener : safeObjectListeners()) {
			listener.onObjectRemoved(this, all);
		}
	}

	private void notifyObjectAdded(IBObject obj) {
		for (ObjectListener listener : safeObjectListeners()) {
			listener.onObjectAdded(this, obj);
		}
	}

	/**
	 * 线程安全地获取 {@link #objectListeners} 副本
	 * @return
	 */
	private List<ObjectListener> safeObjectListeners() {
		synchronized (objectListeners) {
			return Lists.newArrayList(objectListeners);
		}
	}

	@Override
	public int getW() { return w; }

	@Override
	public int getH() { return h; }

	@Override
	public void newSize(int w, int h) {
		this.w = w;
		this.h = h;
	}

}
