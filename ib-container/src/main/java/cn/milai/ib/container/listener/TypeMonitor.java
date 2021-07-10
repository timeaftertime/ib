package cn.milai.ib.container.listener;

import java.util.ArrayList;
import java.util.List;

import cn.milai.ib.container.Container;
import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.item.Item;

/**
 * {@link Container} 某类型对象的监听器，容器关闭后监听到的列表不会清空
 * @author milai
 * @date 2021.02.10
 */
public class TypeMonitor<T extends Item> extends ContainerMonitor {

	private TypeMonitor(Container container, Class<T> clazz) {
		super(container, o -> clazz.isInstance(o));
	}

	public static <T extends Item> TypeMonitor<T> monitor(LifecycleContainer container, Class<T> clazz) {
		return new TypeMonitor<>(container, clazz);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll() { return (List<T>) new ArrayList<>(super.getAll()); }

}
