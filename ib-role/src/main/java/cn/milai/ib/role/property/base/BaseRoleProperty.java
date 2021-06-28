package cn.milai.ib.role.property.base;

import cn.milai.ib.obj.property.BaseProperty;
import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.RoleProperty;

/**
 * {@link RoleProperty} 基础实现
 * @author milai
 * @date 2021.06.25
 */
public class BaseRoleProperty extends BaseProperty implements RoleProperty {

	@Override
	public Role owner() {
		return (Role) super.owner();
	}

	@Override
	public void initProperty() {
		if (!(owner() instanceof Role)) {
			throw new IllegalArgumentException("只允许关联到 Role");
		}
		initRoleProperty();
	}

	/**
	 * 实际初始化，在 {@link #initProperty()} 后调用
	 */
	protected void initRoleProperty() {}

}
