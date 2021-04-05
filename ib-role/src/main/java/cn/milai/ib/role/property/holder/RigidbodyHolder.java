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
	default Rigidbody rigidbody() {
		return getProperty(Rigidbody.class);
	}

	/**
	 * 设置 {@link Rigidbody} 并返回之前的值
	 * @param rigidbody
	 * @return
	 */
	default Rigidbody setRigidbody(Rigidbody rigidbody) {
		return putProperty(Rigidbody.class, rigidbody);
	}
}
