package cn.milai.ib.container.listener;

import java.util.List;

import cn.milai.common.base.Collects;
import cn.milai.ib.container.Container;
import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.Property;

/**
 * 监听有指定 {@link Property} 的 {@link Role} 的  {@link ContainerMonitor}
 * @author milai
 * @date 2021.03.29
 */
public class PropertyMonitor<T extends Property> extends ContainerMonitor {

	private Class<T> c;

	private PropertyMonitor(Container container, Class<T> c) {
		super(container, o -> Role.class.isInstance(o) && ((Role) o).hasProperty(c));
		this.c = c;
	}

	/**
	 * 构造一个监听指定 {@link Container} 中含有指定 {@link Property} 的 {@link PropertyMonitor}
	 * @param container
	 * @param c
	 */
	public static <T extends Property> PropertyMonitor<T> monitor(Container container, Class<T> c) {
		return new PropertyMonitor<>(container, c);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getAll() { return (List<Role>) super.getAll(); }

	/**
	 * 获取监听到的所有属性
	 * @return
	 */
	public List<T> getProps() { return Collects.mapList(getAll(), r -> r.getProperty(c)); };

}
