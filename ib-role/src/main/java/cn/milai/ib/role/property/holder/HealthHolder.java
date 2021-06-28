package cn.milai.ib.role.property.holder;

import cn.milai.ib.obj.IBObject;
import cn.milai.ib.role.property.Health;

/**
 * {@link Health} 持有者
 * @author milai
 * @date 2021.06.25
 */
public interface HealthHolder extends IBObject {

	/**
	 * 获取 {@link Health}
	 * @return
	 */
	default Health getHealth() { return getProperty(Health.class); }

	/**
	 * 设置关联 {@link Health}
	 * @param h
	 */
	default void setHealth(Health h) {
		putProperty(Health.class, h);
	}
}
