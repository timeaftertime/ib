package cn.milai.ib.drama;

import cn.milai.ib.container.Container;

/**
 * 剧本
 * @author milai
 * @date 2020.03.05
 */
public interface Drama {

	/**
	 * 获取剧本唯一标识
	 * @return
	 */
	default String getCode() {
		return getClass().getName();
	}

	/**
	 * 获取剧本名字
	 * @return
	 */
	default String getName() {
		return getCode();
	}

	/**
	 * 在指定容器中运行剧本
	 * 该方法应该尽快响应 Thread.interrup()
	 * 返回时若保留线程中断状态，将退出当前线程
	 * @param container
	 */
	void run(Container container);

}
