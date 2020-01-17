package cn.milai.ib.container.listener;

import cn.milai.ib.annotation.Order;
import cn.milai.ib.container.Container;

/**
 * 监听 @see Container 的监听者
 * @author milai
 *
 */
@Order
public interface RefreshListener {

	/**
	 * 在游戏窗口完成一次刷新时调用
	 * @param form 发生刷新的窗口
	 */
	void afterRefresh(Container container);
}
