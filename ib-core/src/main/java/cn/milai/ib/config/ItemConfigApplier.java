package cn.milai.ib.config;

import cn.milai.ib.geometry.Bounds;
import cn.milai.ib.item.Item;

/**
 * 提供 {@link Item} 配置注入的 {@link ConfigApplier}
 * @author milai
 * @date 2021.07.09
 */
public interface ItemConfigApplier extends ConfigApplier<Item> {

	/**
	 * 注入配置并设置 x, y
	 * @param <O>
	 * @param o
	 * @param x
	 * @param y
	 * @return
	 */
	default <O extends Item> O applyXY(O o, double x, double y) {
		return Bounds.xy(apply(o), x, y);
	}

	/**
	 * 注入配置并设置 centerX, centerY
	 * @param <O>
	 * @param o
	 * @param x
	 * @param y
	 * @return
	 */
	default <O extends Item> O applyCenter(O o, double x, double y) {
		return Bounds.centerXY(apply(o), x, y);
	}

}
