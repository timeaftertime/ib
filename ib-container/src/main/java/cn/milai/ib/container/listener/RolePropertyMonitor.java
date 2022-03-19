package cn.milai.ib.container.listener;

import java.util.List;

import cn.milai.ib.container.Container;
import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.RoleProperty;

/**
 * 监听有指定 {@link RoleProperty} 的 {@link Role} 的  {@link ContainerMonitor}
 * @author milai
 * @date 2021.06.26
 */
public class RolePropertyMonitor<T extends RoleProperty> extends PropertyMonitor<T> {

	private RolePropertyMonitor(Container container, Class<T> clazz) {
		super(container, clazz);
	}

	/**
	 * 创建一个监听有指定 {@link RoleProperty} clazz 的所有 {@link Role} 的 {@link RolePropertyMonitor}
	 * @param <T>
	 * @param container
	 * @param clazz
	 * @return
	 */
	public static <T extends RoleProperty> RolePropertyMonitor<T> monitorRoles(Container container, Class<T> clazz) {
		return new RolePropertyMonitor<>(container, clazz);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getAll() { return (List<Role>) super.getAll(); }

}
