package cn.milai.ib.role.property.holder;

import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.Rotatable;

/**
 * {@link Rotatable} 持有者
 * @author milai
 * @date 2021.03.31
 */
public interface RotatableHolder extends Role {

	/**
	 * 获取持有的 {@link Rotatable}
	 * @return
	 */
	default Rotatable rotatable() {
		return getProperty(Rotatable.class);
	}

	/**
	 * 设置 {@link Rotatable} 并返回之前的 {@link Rotatable}
	 * @param rotatable
	 * @return
	 */
	default Rotatable setRotatable(Rotatable rotatable) {
		return putProperty(Rotatable.class, rotatable);
	}
}
