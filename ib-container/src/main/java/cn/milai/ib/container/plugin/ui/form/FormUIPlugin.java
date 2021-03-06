package cn.milai.ib.container.plugin.ui.form;

import javax.swing.JFrame;

import cn.milai.ib.container.plugin.ui.UIPlugin;

/**
 * 窗口剧本容器
 * 2019.12.06
 * @author milai
 */
public interface FormUIPlugin extends UIPlugin {

	/**
	  获取容器标题
	 * @return
	 */
	String getTitle();

	/**
	 * 设置容器标题
	 */
	void setTitle(String title);

	/**
	 * 获取容器对应的 Form
	 * @return
	 */
	JFrame getForm();

	@Override
	default int getUIW() { return getForm().getWidth(); }

	@Override
	default int getUIH() { return getForm().getHeight(); }

	@Override
	default int getUICH() { return getForm().getContentPane().getHeight(); }

}
