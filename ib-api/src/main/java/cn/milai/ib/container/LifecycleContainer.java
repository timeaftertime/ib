package cn.milai.ib.container;

import cn.milai.ib.container.listener.ContainerLifecycleListener;

/**
 * 有生命周期（可启动、暂停、关闭等操作）的容器
 * 2020.01.16
 * @author milai
 */
public interface LifecycleContainer extends Container {

	/**
	 * 启动容器
	 *  每个实例只能启动一次
	 * @throws ContainerClosedException 若容器已被关闭
	 */
	void start() throws ContainerClosedException;

	/**
	 * 关闭容器
	 * 每个实例只能被关闭一次
	 * 调用 start 前可以调用 close，但 close 后不能再 reset 或 start
	 */
	void close();

	/**
	 * 容器是否出于关闭状态
	 * @return
	 */
	boolean isClosed();

	/**
	 * 暂停或继续容器
	 * @throws ContainerClosedException 若容器已被关闭
	 */
	void switchPause() throws ContainerClosedException;

	/**
	 * 容器是否出于暂停状态
	 * @return
	 */
	boolean isPaused();

	/**
	 * 设置是否固定游戏角色，即是否暂停游戏角色的移动、碰撞检测、存活检测
	 * 重绘和帧数增加不受影响
	 * @throws ContainerClosedException 若容器已被关闭
	 */
	void setPined(boolean pin) throws ContainerClosedException;

	/**
	 * 添加一个容器生命周期事件的监听者
	 * @param listener
	 * @throws ContainerClosedException 若容器已被关闭
	 */
	void addLifeCycleListener(ContainerLifecycleListener listener) throws ContainerClosedException;

}
