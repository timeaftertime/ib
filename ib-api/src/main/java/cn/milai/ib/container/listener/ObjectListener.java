package cn.milai.ib.container.listener;

import java.util.List;

import cn.milai.ib.IBObject;
import cn.milai.ib.container.lifecycle.LifecycleContainer;

/**
 * {@link LifecycleContainer} 对象事件监听器
 * @author milai
 * @date 2021.02.17
 */
public interface ObjectListener extends ContainerListener {

	/**
	 * 游戏对象被加入后调用
	 * @param obj
	 */
	default void onObjectAdded(IBObject obj) {}

	/**
	 * 游戏对象被移除后被调用
	 * @param objs 被移除的对象
	 */
	default void onObjectRemoved(List<IBObject> objs) {}

}
