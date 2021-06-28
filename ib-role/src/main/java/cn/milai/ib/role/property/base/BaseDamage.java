package cn.milai.ib.role.property.base;

import cn.milai.ib.role.property.Damage;

/**
 * {@link Damage} 默认实现
 * @author milai
 * @date 2021.03.31
 */
public class BaseDamage extends BaseRoleProperty implements Damage {

	private int value;

	@Override
	public int getValue() { return value; }

	@Override
	public void setValue(int value) { this.value = value; }

}
