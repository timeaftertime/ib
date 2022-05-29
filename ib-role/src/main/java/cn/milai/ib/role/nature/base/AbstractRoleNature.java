package cn.milai.ib.role.nature.base;

import cn.milai.ib.actor.nature.AbstractNature;
import cn.milai.ib.role.Role;
import cn.milai.ib.role.nature.RoleNature;

/**
 * {@link RoleNature} 抽象实现
 * @author milai
 * @date 2021.06.25
 */
public abstract class AbstractRoleNature extends AbstractNature implements RoleNature {

	public AbstractRoleNature(Role owner) {
		super(owner);
	}

	@Override
	public Role owner() {
		return (Role) super.owner();
	}

}
