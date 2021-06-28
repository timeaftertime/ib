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
	default Explosible getExplosible() { return getProperty(Explosible.class); }

	/**
	 * 设置关联的 {@link Explosible}
	 * @param e
	 */
	default void setExplosible(Explosible e) {
		putProperty(Explosible.class, e);
	}

}
