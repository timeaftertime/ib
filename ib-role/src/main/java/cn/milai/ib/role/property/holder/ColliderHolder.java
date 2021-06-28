package cn.milai.ib.role.property.holder;

import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.Collider;

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
	default Collider getCollider() { return getProperty(Collider.class); }

	/**
	 * 设置持有的 {@link Collider}
	 * @param c
	 */
	default void setCollider(Collider c) {
		putProperty(Collider.class, c);
	}

}
