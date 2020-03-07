package cn.milai.ib.container.listener;

import cn.milai.ib.obj.IBObject;

/**
 * 容器事件监听器
 * @author milai
 */
public interface ContainerEventListener {

	/**
	 * 游戏对象被加入时调用
	 * @param obj
	 */
	void onObjectAdded(IBObject obj);

	/**
	 * 游戏对象被移除时被调用
	 * @param obj 被移除的对象
	 */
	void onObjectRemoved(IBObject obj);

}
