package cn.milai.ib.actor.nature;

import cn.milai.ib.actor.Actor;

/**
 * {@link Actor} 的性质、特性
 * @author milai
 * @date 2021.03.28
 */
public interface Nature {

	/**
	 * 获取当前 {@link Nature} 关联的 {@link Actor}
	 * @return
	 */
	Actor owner();

	/**
	 * 获取标识
	 */
	String name();

}
