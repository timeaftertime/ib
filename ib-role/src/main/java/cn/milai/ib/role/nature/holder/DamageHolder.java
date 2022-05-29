package cn.milai.ib.role.nature.holder;

import cn.milai.ib.role.Role;
import cn.milai.ib.role.nature.Damage;

/**
 * {@link Damage} 持有者
 * @author milai
 * @date 2021.03.31
 */
public interface DamageHolder extends Role {

	/**
	 * 获取持有的 {@link Damage}
	 * @return
	 */
	default Damage getDamage() { return getNature(Damage.NAME); }

	/**
	 * 设置持有的 {@link Damage}
	 * @param d
	 */
	default void setDamage(Damage d) {
		putNature(d);
	}

}
