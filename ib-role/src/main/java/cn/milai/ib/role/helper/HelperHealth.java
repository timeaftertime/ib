package cn.milai.ib.role.helper;

import cn.milai.ib.role.Role;
import cn.milai.ib.role.nature.Health;
import cn.milai.ib.role.nature.base.BaseHealth;

/**
 * {@link Helper} 的 {@link Health}
 * @author milai
 * @date 2021.06.25
 */
public class HelperHealth extends BaseHealth {

	public HelperHealth(Role owner) {
		super(owner);
	}

	@Override
	public synchronized void changeHP(Role character, int life) {
		if (character != owner()) {
			return;
		}
		super.changeHP(null, life);
	}

}
