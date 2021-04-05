package cn.milai.ib.role.property.base;

import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.Property;

/**
 * {@link Property} 默认实现
 * @author milai
 * @date 2021.03.28
 */
public class BaseProperty implements Property {

	private Role role;

	public BaseProperty(Role role) {
		this.role = role;
	}

	@Override
	public Role getRole() { return role; }

}
