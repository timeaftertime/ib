package cn.milai.ib.container;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import cn.milai.beginning.collection.Filter;
import cn.milai.ib.container.listener.ContainerListener;
import cn.milai.ib.container.listener.ItemListener;
import cn.milai.ib.item.Item;
import cn.milai.ib.item.property.Property;

/**
 * {@link Container} 默认实现
 * @author milai
 * @date 2021.02.06
 */
public class BaseContainer implements Container {

	private Set<Item> items = Collections.newSetFromMap(new ConcurrentHashMap<>());

	/**
	 * 容器对象事件监听器列表
	 */
	private List<ItemListener> itemListeners = new CopyOnWriteArrayList<>();

	@Override
	public boolean addObject(Item obj) {
		obj.init(this);
		if (items.add(obj)) {
			notifyItemAdded(obj);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeObject(Item obj) {
		if (items.remove(obj)) {
			notifyItemRemoved(obj);
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Item> List<T> getAll(Class<T> type) {
		if (type == Item.class) {
			return (List<T>) new ArrayList<>(items);
		}
		List<T> allOfType = new ArrayList<>();
		for (Item o : items) {
			if (type.isInstance(o))
				allOfType.add((T) o);
		}
		return allOfType;
	}

	@Override
	public <T extends Property> List<T> getProps(Class<T> type) {
		List<T> allOfType = new ArrayList<>();
		for (Item i : items) {
			T p = i.getProperty(type);
			if (p != null) {
				allOfType.add(p);
			}
		}
		return allOfType;
	}

	@Override
	public void reset() {
		notifyItemCleared();
		items.clear();
		itemListeners = Filter.nlist(itemListeners, ContainerListener::inEpoch);
	}

	@Override
	public void addItemListener(ItemListener listener) {
		itemListeners.add(listener);
	}

	@Override
	public void removeItemListener(ItemListener listener) {
		itemListeners.remove(listener);
	}

	private void notifyItemRemoved(Item obj) {
		notifyItemsRemoved(Collections.unmodifiableList(Arrays.asList(obj)));
	}

	private void notifyItemCleared() {
		notifyItemsRemoved(Collections.unmodifiableList(getAll(Item.class)));
	}

	private void notifyItemsRemoved(List<Item> all) {
		for (ItemListener listener : new ArrayList<>(itemListeners)) {
			listener.onRemoved(this, all);
		}
	}

	private void notifyItemAdded(Item obj) {
		for (ItemListener listener : new ArrayList<>(itemListeners)) {
			listener.onAdded(this, obj);
		}
	}

}
