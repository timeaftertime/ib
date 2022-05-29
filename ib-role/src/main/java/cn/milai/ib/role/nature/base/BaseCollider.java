package cn.milai.ib.role.nature.base;

import cn.milai.ib.role.Role;
import cn.milai.ib.role.nature.Collider;

/**
 * {@link Collider} 默认实现
 * @author milai
 * @date 2021.03.29
 */
public class BaseCollider extends AbstractRoleNature implements Collider {

	public BaseCollider(Role owner) {
		super(owner);
	}

}
