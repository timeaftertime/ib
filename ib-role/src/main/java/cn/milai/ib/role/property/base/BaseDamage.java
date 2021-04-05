package cn.milai.ib.role.property.base;

import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.Damage;

/**
 * {@link Damage} 默认实现
 * @author milai
 * @date 2021.03.31
 */
public class BaseDamage extends BaseProperty implements Damage {

	private final int value;
	
	public BaseDamage(Role role, int value) {
		super(role);
		this.value = value;
	}

	@Override
	public int getValue() { return value; }

}
