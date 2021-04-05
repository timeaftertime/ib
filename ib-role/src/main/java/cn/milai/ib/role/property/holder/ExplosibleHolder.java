package cn.milai.ib.role.property.holder;

import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.Explosible;

/**
 * {@link Explosible} 持有者
 * @author milai
 * @date 2021.04.01
 */
public interface ExplosibleHolder extends Role {

	/**
	 * 获取持有的 {@link Explosible} 对象
	 * @return
	 */
	default Explosible explosible() {
		return getProperty(Explosible.class);
	}

	/**
	 * 设置 {@link Explosible} 并返回之前设置的值
	 * @param explosible
	 * @return
	 */
	default Explosible setExplosible(Explosible explosible) {
		return putProperty(Explosible.class, explosible);
	}

}
