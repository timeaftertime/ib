package cn.milai.ib.interaction.form.listener;

import cn.milai.ib.annotation.Order;

/**
 * 窗口容器关闭事件监听器
 * 2020.01.15
 * @author milai
 */
@Order
public interface FormCloseListener {

	/**
	 * 容器被关闭前调用
	 */
	void onFormClosed();

}
