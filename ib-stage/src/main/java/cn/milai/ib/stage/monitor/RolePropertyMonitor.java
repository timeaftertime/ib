package cn.milai.ib.stage.monitor;

import java.util.List;

import cn.milai.ib.role.Role;
import cn.milai.ib.role.nature.RoleNature;
import cn.milai.ib.stage.Stage;

/**
 * 监听有指定 {@link RoleNature} 的 {@link Role} 的  {@link ActorMonitor}
 * @author milai
 * @date 2021.06.26
 */
public class RolePropertyMonitor<T extends RoleNature> extends PropertyMonitor<T> {

	private RolePropertyMonitor(Stage stage, String name) {
		super(stage, name);
	}

	/**
	 * 创建一个监听有指定 {@link RoleNature} 的所有 {@link Role} 的 {@link RolePropertyMonitor}
	 * @param <T>
	 * @param stage
	 * @param name
	 * @return
	 */
	public static <T extends RoleNature> RolePropertyMonitor<T> monitorRoles(Stage stage, String name) {
		return new RolePropertyMonitor<>(stage, name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getAll() { return (List<Role>) super.getAll(); }

}
