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
	default Rotatable getRotatable() { return getProperty(Rotatable.class); }

	/**
	 * 设置关联的 {@link Rotatable}
	 * @param r
	 */
	default void setRotatable(Rotatable r) {
		putProperty(Rotatable.class, r);
	}

}
