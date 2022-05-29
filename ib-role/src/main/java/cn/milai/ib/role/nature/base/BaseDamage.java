package cn.milai.ib.role.nature.base;

import cn.milai.ib.actor.config.Configurable;
import cn.milai.ib.role.Role;
import cn.milai.ib.role.nature.Damage;

/**
 * {@link Damage} 默认实现
 * @author milai
 * @date 2021.03.31
 */
public class BaseDamage extends AbstractRoleNature implements Damage {
	
	private int value;

	public BaseDamage(Role owner) {
		super(owner);
	}

	@Override
	public int getValue() { return value; }

	@Configurable
	@Override
	public void setValue(int value) { this.value = value; }

	@Override
	public String toString() {
		return "BaseDamage [" + value + "]";
	}

}
