package cn.milai.ib.container.lifecycle;

import cn.milai.ib.container.CloseableContainer;
import cn.milai.ib.container.Container;
import cn.milai.ib.container.ContainerClosedException;
import cn.milai.ib.container.listener.LifecycleListener;
import cn.milai.ib.container.listener.ObjectListener;

/**
 * 有生命周期（可启动、暂停、关闭等操作）的容器
 * 2020.01.16
 * @author milai
 */
public interface LifecycleContainer extends CloseableContainer {

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
	 */
	boolean isPaused();

	/**
	 * 设置是否固定游戏角色，即是否暂停游戏角色的移动、碰撞检测、存活检测
	 * 重绘和帧数增加不受影响
	 * @param pined
	 * @throws ContainerClosedException 若容器已被关闭
	 */
	void setPined(boolean pined) throws ContainerClosedException;

	/**
	 * 返回容器中游戏角色是否被固定
	 * @return
	 */
	boolean isPined();

	/**
	 * 返回容器是否已经调用 {@link #start()} 且没有调用 {@link #close()}
	 * @return
	 */
	boolean isRunning();

	/**
	 * 获取当前累计的帧数
	 * @return
	 */
	long getFrame();

	/**
	 * 添加容器生命周期监听器
	 * @param listener
	 */
	void addLifecycleListener(LifecycleListener listener);

	/**
	 * 移除容器生命周期监听器
	 * @param listener
	 */
	void removeLifecycleListener(LifecycleListener listener);

	/**
	 * 获取当前纪元，即调用 {@link #reset()} 的次数 + 1
	 * @return
	 */
	int getEpoch();

	/**
	 * 重置容器。
	 * 移除所有游戏对象、所有 {@link LifecycleListener#acrossEpoch()} 为 false 的监听器。
	 * 帧数不会清零。
	 * {@link LifecycleListener#onEpochChanged(Container)} 将在容器中对象被清空后、监听器被移除前调用，
	 * {@link ObjectListener#onObjectRemoved(java.util.List)} 将在游戏对象被清空、监听器被移除前调用。
	 * @throws ContainerClosedException
	 */
	void reset() throws ContainerClosedException;

}
