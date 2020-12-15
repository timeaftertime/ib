package cn.milai.ib.container.ui.form;

import javax.swing.JFrame;

import cn.milai.ib.container.DramaContainer;

/**
 * 窗口剧本容器
 * 2019.12.06
 * @author milai
 */
public interface FormContainer extends DramaContainer {

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

}
