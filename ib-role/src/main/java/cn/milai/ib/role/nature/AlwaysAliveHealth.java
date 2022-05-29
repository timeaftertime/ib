package cn.milai.ib.role.nature;

import cn.milai.ib.role.Role;

/**
 * {@link Health#isAlive()} 始终返回 true 的 {@link Health}
 * @author milai
 * @date 2022.05.26
 */
public class AlwaysAliveHealth implements RoleNature, Health {

	private Role owner;

	public AlwaysAliveHealth(Role owner) {
		this.owner = owner;
	}

	@Override
	public boolean isAlive() { return true; }

	@Override
	public Role owner() {
		return owner;
	}

	@Override
	public int getInitHP() { return 1; }

	@Override
	public int getHP() { return 1; }

	@Override
	public void setHP(int hp) {
	}

	@Override
	public void changeHP(Role from, int hp) {
	}

	@Override
	public Role lastAttacker() {
		return null;
	}

}
