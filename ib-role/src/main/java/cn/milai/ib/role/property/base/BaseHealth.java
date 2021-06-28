package cn.milai.ib.role.property.base;

import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.Health;

/**
 * {@link Health} 默认实现
 * @author milai
 * @date 2021.06.25
 */
public class BaseHealth extends BaseRoleProperty implements Health {

	private int life;

	private int initLife;

	private Role lastAttacker;

	@Override
	public int initHP() {
		if (owner() == null) {
			throw new IllegalStateException("尚未设置 initLife");
		}
		return initLife;
	}

	@Override
	protected void initRoleProperty() {
		initLife = life;
	}

	@Override
	public int getHP() { return life; }

	@Override
	public void setHP(int life) {
		if (owner() != null) {
			throw new IllegalStateException("只能在初始化前调用");
		}
		this.life = life;
	}

	@Override
	public boolean isAlive() { return life > 0; }

	@Override
	public synchronized void changeHP(Role from, int life) {
		if (this.life > 0 && life < 0) {
			lastAttacker = from;
		}
		long pre = this.life;
		if (pre + life > Integer.MAX_VALUE) {
			this.life = Integer.MAX_VALUE;
		} else if (pre + life < 0) {
			this.life = 0;
		} else {
			this.life = (int) (pre + life);
		}
	}

	@Override
	public Role lastAttacker() {
		return lastAttacker;
	}

}
