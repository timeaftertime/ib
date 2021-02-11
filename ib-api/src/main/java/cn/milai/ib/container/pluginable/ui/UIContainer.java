package cn.milai.ib.container.pluginable.ui;

import cn.milai.ib.container.plugin.PluginableContainer;
import cn.milai.ib.container.plugin.ui.Image;
import cn.milai.ib.container.plugin.ui.UIPlugin;

/**
 * 可能持有 {@link UIPlugin} 的容器
 * @author milai
 * @date 2021.02.11
 */
public interface UIContainer extends PluginableContainer {

	/**
	 * 设置 UI 背景
	 * @param img
	 */
	default void setBackgroud(Image img) {
		fire(UIPlugin.class, ui -> ui.setBackgroud(img));
	}

	/**
	 * 重新设置容器实际的、显示的宽度和高度为 width, height 并保持显示中心位置不变
	 * @param width
	 * @param height
	 */
	default void resizeWithUI(int width, int height) {
		fire(UIPlugin.class, ui -> ui.resizeWithUI(width, height));
	}

	/**
	 * 重新设置容器的显示宽度和高度并使中心位置回到屏幕中心
	 * @param width
	 * @param height
	 */
	default void resizeUI(int width, int height) {
		fire(UIPlugin.class, ui -> ui.resizeUI(width, height));
	}
}
