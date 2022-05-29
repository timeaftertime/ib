package cn.milai.ib.role.nature.holder;

import cn.milai.ib.role.Role;
import cn.milai.ib.role.nature.Collider;

/**
 * {@link Collider} 持有者
 * @author milai
 * @date 2021.03.31
 */
public interface ColliderHolder extends Role {

	/**
	 * 获取持有的 {@link Collider}
	 * @return
	 */
	default Collider getCollider() { return getNature(Collider.NAME); }

	/**
	 * 设置持有的 {@link Collider}
	 * @param c
	 */
	default void setCollider(Collider c) {
		putNature(c);
	}

}
