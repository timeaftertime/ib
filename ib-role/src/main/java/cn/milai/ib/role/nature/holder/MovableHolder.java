package cn.milai.ib.role.nature.holder;

import cn.milai.ib.role.Role;
import cn.milai.ib.role.nature.Movable;

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
	default Movable getMovable() { return getNature(Movable.NAME); }

	/**
	 * 设置关联的 {@link Movable}
	 * @param m
	 */
	default void setMovable(Movable m) {
		putNature(m);
	}

}
