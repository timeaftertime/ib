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
	default Damage getDamage() { return getProperty(Damage.class); }

	/**
	 * 设置持有的 {@link Damage}
	 * @param d
	 */
	default void setDamage(Damage d) {
		putProperty(Damage.class, d);
	}

}
