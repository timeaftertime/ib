package cn.milai.ib.container.form;

import java.awt.event.MouseListener;

import cn.milai.ib.container.LifecycleContainer;
import cn.milai.ib.container.form.listener.KeyboardListener;

/**
 * 窗口容器
 * 2019.12.06
 * @author milai
 */
public interface FormContainer extends LifecycleContainer {

	/**
	 * 添加键盘监听器
	 * 
	 * @param listener
	 */
	void addKeyboardListener(KeyboardListener listener);

	/**
	 * 移除键盘监听器
	 * 
	 * @param listener
	 */
	void removeKeyboardListener(KeyboardListener listener);

	/**
	 * 将下一个与 component 关联的鼠标事件通知 listener
	 * @param component
	 * @param listener
	 */
	void notifyOnce(FormComponent component, MouseListener listener);

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
