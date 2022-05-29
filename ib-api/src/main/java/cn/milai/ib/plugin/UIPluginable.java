package cn.milai.ib.plugin;

import cn.milai.ib.plugin.ui.Image;
import cn.milai.ib.plugin.ui.UICrew;

/**
 * 持有 {@link UICrew}
 * @author milai
 * @date 2021.02.11
 */
public interface UIPluginable extends Pluginable<Crew> {

	/**
	 * 设置 UI 背景
	 * @param img
	 */
	default void setBackgroud(Image img) {
		fire(UICrew.class, p -> p.setBackgroud(img));
	}

}
