package cn.milai.ib.role.property.base;

import cn.milai.ib.config.Configurable;
import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.Health;

/**
 * {@link Health} 默认实现
 * @author milai
 * @date 2021.06.25
 */
public class BaseHealth extends BaseRoleProperty implements Health {

	private int hp;

	private int initHP;

	private Role lastAttacker;

	@Override
	public int initHP() {
		if (owner() == null) {
			throw new IllegalStateException("尚未设置 initLife");
		}
		return initHP;
	}

	@Override
	protected void initRoleProperty() {
		initHP = hp;
	}

	@Override
	public int getHP() { return hp; }

	@Configurable
	@Override
	public void setHP(int hp) {
		if (owner() != null) {
			throw new IllegalStateException("只能在初始化前调用");
		}
		this.hp = hp;
	}

	@Override
	public boolean isAlive() { return hp > 0; }

	@Override
	public synchronized void changeHP(Role from, int hp) {
		if (this.hp > 0 && hp < 0) {
			lastAttacker = from;
		}
		long pre = this.hp;
		if (pre + hp > Integer.MAX_VALUE) {
			this.hp = Integer.MAX_VALUE;
		} else if (pre + hp < 0) {
			this.hp = 0;
		} else {
			this.hp = (int) (pre + hp);
		}
	}

	@Override
	public Role lastAttacker() {
		return lastAttacker;
	}

	@Override
	public String toString() {
		return "BaseHealth [" + hp + "]";
	}

}
