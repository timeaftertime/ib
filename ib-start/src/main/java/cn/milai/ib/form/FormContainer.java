package cn.milai.ib.form;

import cn.milai.ib.container.Container;
import cn.milai.ib.form.listener.KeyboardListener;

/**
 * 窗口容器
 *
 * 2019.12.06
 *
 * @author milai
 */
public interface FormContainer extends Container {

	/**
	 * 启动容器
	 */
	void start();

	/**
	 * 重置容器
	 */
	void reset();

	/**
	 * 关闭容器
	 */
	void close();

	/**
	 * 添加键盘监听器
	 * 
	 * @param playerController
	 */
	void addKeyboardListener(KeyboardListener listener);

}
