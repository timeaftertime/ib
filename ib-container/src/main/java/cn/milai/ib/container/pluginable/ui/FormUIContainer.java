package cn.milai.ib.container.pluginable.ui;

import cn.milai.ib.container.plugin.ui.form.FormUIPlugin;

/**
 * 可能持有 {@link FormUIPlugin} 的 {@link UIContainer}
 * @author milai
 * @date 2021.02.11
 */
public interface FormUIContainer extends UIContainer {

	/**
	 * 获取窗口标题，若当前容器没有 {@link FormUIContainer} ，返回空字符串
	 * @return
	 */
	default String getTitle() { return fire(FormUIPlugin.class, form -> form.getTitle(), ""); }

	/**
	 * 设置窗口标题
	 * @param title
	 */
	default void setTitle(String title) {
		fire(FormUIPlugin.class, form -> form.setTitle(title));
	}
}
