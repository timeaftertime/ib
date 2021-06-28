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
	default Movable getMovable() { return getProperty(Movable.class); }

	/**
	 * 设置关联的 {@link Movable}
	 * @param m
	 */
	default void setMovable(Movable m) {
		putProperty(Movable.class, m);
	}

}
