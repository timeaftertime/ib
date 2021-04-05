package cn.milai.ib.role.property.holder;

import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.Movable;

/**
 * 持有 {@link Movable}
 * @author milai
 * @date 2021.03.31
 */
public interface MovableHolder extends Role {

	/**
	 * 获取持有的 {@link Movable}
	 * @return
	 */
	default Movable movable() {
		return getProperty(Movable.class);
	}

	/**
	 * 设置 {@link Movable} 并返回之前的 {@link Movable}
	 * @param movable
	 * @return
	 */
	default Movable setMovable(Movable movable) {
		return putProperty(Movable.class, movable);
	}
}
