package cn.milai.ib.container.listener;

import cn.milai.ib.obj.IBObject;

/**
 * 游戏事件监听器
 *
 * @author milai
 */
public interface GameEventListener {

	/**
	 * 游戏对象被移除时被调用
	 * 
	 * @param obj 被移除的对象
	 */
	void onObjectRemoved(IBObject obj);
	
	/**
	 * 容器被关闭前调用
	 */
	void onFormClosed();
	
}
