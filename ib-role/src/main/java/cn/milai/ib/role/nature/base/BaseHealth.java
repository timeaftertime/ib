package cn.milai.ib.role.nature.base;

import cn.milai.ib.actor.config.Configurable;
import cn.milai.ib.role.Role;
import cn.milai.ib.role.nature.Health;

/**
 * {@link Health} 默认实现
 * @author milai
 * @date 2021.06.25
 */
public class BaseHealth extends AbstractRoleNature implements Health {

	@Configurable
	private int hp;

	@Configurable("hp")
	private int initHP;

	private Role lastAttacker;

	public BaseHealth(Role owner) {
		super(owner);
	}

	@Override
	public int getInitHP() { return initHP; }

	@Override
	public int getHP() { return hp; }

	@Override
	public void setHP(int hp) { this.hp = hp; }

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
