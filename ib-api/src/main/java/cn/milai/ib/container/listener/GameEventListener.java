package cn.milai.ib.container.listener;

import cn.milai.ib.GameObject;

/**
 * 游戏事件监听器
 *
 * @author milai
 */
public interface GameEventListener {

	/**
	 * 游戏对象死亡时被调用
	 * @param obj
	 */
	void onGameObjectDead(GameObject obj);
	
	/**
	 * 游戏窗口被关闭前调用
	 */
	void onFormClosed();
	
}