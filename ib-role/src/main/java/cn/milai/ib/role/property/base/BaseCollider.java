package cn.milai.ib.role.property.base;

import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.Collider;

/**
 * {@link Collider} 默认实现
 * @author milai
 * @date 2021.03.29
 */
public class BaseCollider extends BaseProperty implements Collider {

	public BaseCollider(Role role) {
		super(role);
	}

	@Override
	public void onCrash(Collider collider) {}

}
