package cn.milai.ib.container.listener;

import cn.milai.ib.IBObject;
import cn.milai.ib.annotation.Order;

/**
 * 游戏事件监听器
 * @author milai
 */
@Order
public interface GameEventListener {

	/**
	 * 游戏对象被移除时被调用
	 * @param obj 被移除的对象
	 */
	void onObjectRemoved(IBObject obj);
	
}
