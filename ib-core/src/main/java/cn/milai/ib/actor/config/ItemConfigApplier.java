package cn.milai.ib.actor.config;

import cn.milai.ib.actor.Actor;

/**
 * 提供 {@link Actor} 配置注入的 {@link ConfigApplier}
 * @author milai
 * @date 2021.07.09
 */
public interface ItemConfigApplier extends ConfigApplier<Actor> {

	/**
	 * 注入配置并设置 x, y
	 * @param <O>
	 * @param o
	 * @param x
	 * @param y
	 * @return
	 */
	default <O extends Actor> O applyXY(O o, double x, double y) {
		return apply(o).xy(x, y);
	}

	/**
	 * 注入配置并设置 centerX, centerY
	 * @param <O>
	 * @param o
	 * @param x
	 * @param y
	 * @return
	 */
	default <O extends Actor> O applyCenter(O o, double x, double y) {
		return apply(o).centerXY(x, y);
	}

}
