package cn.milai.ib.role.nature.holder;

import cn.milai.ib.actor.Actor;
import cn.milai.ib.role.nature.Health;

/**
 * {@link Health} 持有者
 * @author milai
 * @date 2021.06.25
 */
public interface HealthHolder extends Actor {

	/**
	 * 获取 {@link Health}
	 * @return
	 */
	default Health getHealth() { return getNature(Health.NAME); }

	/**
	 * 设置关联 {@link Health}
	 * @param h
	 */
	default void setHealth(Health h) {
		putNature(h);
	}
}
