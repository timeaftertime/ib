package cn.milai.ib.role.property.holder;

import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.Damage;

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
	default Damage damage() {
		return getProperty(Damage.class);
	}

	/**
	 * 设定 {@link Damage}，返回之前设定的 {@link Damage}
	 * @param damage
	 * @return
	 */
	default Damage setDamage(Damage damage) {
		return putProperty(Damage.class, damage);
	}
}
