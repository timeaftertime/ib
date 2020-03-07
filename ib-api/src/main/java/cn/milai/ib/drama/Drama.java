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
	 * 该方法应尽可能快速地响应 Thread.interrupt()
	 * 响应时应立即中断剧本执行并返回，同时保持 Thread.isInterrpted() == true
	 * @param container
	 */
	void run(Container container);

}
