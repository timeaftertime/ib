package cn.milai.ib.container.pluginable.ui;

import cn.milai.ib.container.plugin.ui.Image;
import cn.milai.ib.container.plugin.ui.UIPlugin;
import cn.milai.ib.container.pluginable.PluginableContainer;

/**
 * 持有 {@link UIPlugin} 的容器
 * @author milai
 * @date 2021.02.11
 */
public interface UIContainer extends PluginableContainer {

	/**
	 * 设置 UI 背景
	 * @param img
	 */
	default void setBackgroud(Image img) {
		fire(UIPlugin.class, p -> p.setBackgroud(img));
	}

	/**
	 * 重新设置 UI 宽度和高度为 w, h
	 * @param w
	 * @param h
	 */
	default void resize(double w, double h) {
		fire(UIPlugin.class, p -> p.resize(w, h));
	}

}
