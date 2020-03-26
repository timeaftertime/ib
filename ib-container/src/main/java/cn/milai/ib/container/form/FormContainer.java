package cn.milai.ib.container.form;

import cn.milai.ib.container.LifecycleContainer;

/**
 * 窗口容器
 * 2019.12.06
 * @author milai
 */
public interface FormContainer extends LifecycleContainer {

	/**
	  获取容器标题
	 * @return
	 */
	String getTitle();

	/**
	 * 设置容器标题
	 */
	void setTitle(String title);

}
