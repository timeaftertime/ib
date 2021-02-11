package cn.milai.ib.container;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import cn.milai.ib.IBObject;

/**
 * {@link Container} 默认实现
 * @author milai
 * @date 2021.02.06
 */
public class BaseContainer implements Container {

	private int w;
	private int h;

	private List<IBObject> objs;

	private Set<IBObject> exists = Sets.newHashSet();

	public BaseContainer() {
		objs = Lists.newCopyOnWriteArrayList();
	}

	@Override
	public synchronized boolean addObject(IBObject obj) {
		return exists.add(obj) && objs.add(obj);
	}

	@Override
	public synchronized boolean removeObject(IBObject obj) {
		return exists.remove(obj) && objs.remove(obj);
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
	}

	@Override
	public int getW() { return w; }

	@Override
	public int getH() { return h; }

	@Override
	public void resize(int w, int h) {
		this.w = w;
		this.h = h;
	}

}
