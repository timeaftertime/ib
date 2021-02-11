package cn.milai.ib.container.lifecycle;

import cn.milai.ib.IBObject;
import cn.milai.ib.container.CloseableIBContainer;
import cn.milai.ib.container.Container;
import cn.milai.ib.container.ContainerClosedException;

/**
 * 有生命周期（可启动、暂停、关闭等操作）的容器
 * 2020.01.16
 * @author milai
 */
public interface LifecycleContainer extends CloseableIBContainer {

	/**
	 * 启动容器。多次调用该方法将被忽略
	 * @throws ContainerClosedException 若容器已被关闭
	 */
	void start() throws ContainerClosedException;

	/**
	 * 暂停或继续容器
	 * @throws ContainerClosedException 若容器已被关闭
	 */
	void switchPause() throws ContainerClosedException;

	/**
	 * 容器是否出于暂停状态
	 * @return
	 * @throws ContainerClosedException 若容器已被关闭
	 */
	boolean isPaused() throws ContainerClosedException;

	/**
	 * 设置是否固定游戏角色，即是否暂停游戏角色的移动、碰撞检测、存活检测
	 * 重绘和帧数增加不受影响
	 * @throws ContainerClosedException 若容器已被关闭
	 */
	void setPined(boolean pin) throws ContainerClosedException;

	/**
	 * 返回容器中游戏角色是否被固定
	 * @return
	 * @throws ContainerClosedException
	 */
	boolean isPined() throws ContainerClosedException;

	/**
	 * 获取当前累计的帧数，获取失败则返回 -1
	 * 
	 * @return
	 */
	long getFrame();

	/**
	 * 添加游戏事件监听器
	 * @param listener
	 * @throws ContainerClosedException
	 */
	void addEventListener(ContainerEventListener listener) throws ContainerClosedException;

	/**
	 * 移除游戏事件监听器
	 * @param listener
	 */
	void removeEventListener(ContainerEventListener listener);

	/**
	 * 获取纪元
	 * @return
	 */
	int getEpoch();

	/**
	 * 重置容器
	 * 移除所有游戏对象和监听器，帧数不会清零
	 * {@link ContainerEventListener#afterEpochChanged(Container)} 将在容器中对象被清空后调用
	 * {@link ContainerEventListener#onObjectRemoved(IBObject)} 不会被调用
	 * @throws ContainerClosedException
	 */
	void reset() throws ContainerClosedException;

}
