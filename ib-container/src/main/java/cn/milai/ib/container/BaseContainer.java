package cn.milai.ib.container;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

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

	private Set<IBObject> objs = Collections.newSetFromMap(new ConcurrentHashMap<>());

	/**
	 * 容器对象事件监听器列表
	 */
	private List<ObjectListener> objectListeners = new CopyOnWriteArrayList<>();

	@Override
	public boolean addObject(IBObject obj) {
		if (objs.add(obj)) {
			notifyObjectAdded(obj);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeObject(IBObject obj) {
		if (objs.remove(obj)) {
			notifyObjectRemoved(obj);
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IBObject> List<T> getAll(Class<T> type) {
		if (type == IBObject.class) {
			return (List<T>) new ArrayList<>(objs);
		}
		List<T> allOfType = new ArrayList<>();
		for (IBObject o : objs) {
			if (type.isInstance(o))
				allOfType.add((T) o);
		}
		return allOfType;
	}

	@Override
	public void reset() {
		notifyObjectCleared();
		objs.clear();
		objectListeners = Collects.unfilterList(objectListeners, ContainerListener::inEpoch);
	}

	@Override
	public void addObjectListener(ObjectListener listener) {
		objectListeners.add(listener);
	}

	@Override
	public void removeObjectListener(ObjectListener listener) {
		objectListeners.remove(listener);
	}

	private void notifyObjectRemoved(IBObject obj) {
		notifyObjectsRemoved(Collections.unmodifiableList(Arrays.asList(obj)));
	}

	private void notifyObjectCleared() {
		notifyObjectsRemoved(Collections.unmodifiableList(getAll(IBObject.class)));
	}

	private void notifyObjectsRemoved(List<IBObject> all) {
		for (ObjectListener listener : new ArrayList<>(objectListeners)) {
			listener.onObjectRemoved(this, all);
		}
	}

	private void notifyObjectAdded(IBObject obj) {
		for (ObjectListener listener : new ArrayList<>(objectListeners)) {
			listener.onObjectAdded(this, obj);
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
