package cn.milai.ib.container.listener;

import java.util.List;

import cn.milai.beginning.collection.Mapping;
import cn.milai.ib.container.Container;
import cn.milai.ib.item.Item;
import cn.milai.ib.item.property.Property;

/**
 * 监听有指定 {@link Property} 的 {@link Item} 的  {@link ContainerMonitor}
 * @author milai
 * @date 2021.03.29
 */
public class PropertyMonitor<T extends Property> extends ContainerMonitor {

	private Class<T> clazz;

	protected PropertyMonitor(Container container, Class<T> clazz) {
		super(container, o -> o.hasProperty(clazz));
		this.clazz = clazz;
	}

	/**
	 * 构造一个监听指定 {@link Container} 中含有指定 {@link Property} 的 {@link PropertyMonitor}
	 * @param container
	 * @param clazz;
	 */
	public static <T extends Property> PropertyMonitor<T> monitor(Container container, Class<T> clazz) {
		return new PropertyMonitor<>(container, clazz);
	}

	/**
	 * 获取监听到的所有属性
	 * @return
	 */
	public List<T> getProps() { return Mapping.list(getAll(), r -> r.getProperty(clazz)); };

	@Override
	public List<? extends Item> getAll() { return super.getAll(); }

}
