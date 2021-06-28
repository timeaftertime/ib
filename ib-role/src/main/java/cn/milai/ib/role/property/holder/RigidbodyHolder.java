package cn.milai.ib.role.property.holder;

import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.Rigidbody;

/**
 * {@link Rigidbody} 持有者
 * @author milai
 * @date 2021.04.02
 */
public interface RigidbodyHolder extends Role {

	/**
	 * 获取持有的 {@link Rigidbody}
	 * @return
	 */
	default Rigidbody getRigidbody() { return getProperty(Rigidbody.class); }

	/**
	 * 设置关联 {@link Rigidbody}
	 * @param r
	 */
	default void setRigidbody(Rigidbody r) {
		putProperty(Rigidbody.class, r);
	}

}
