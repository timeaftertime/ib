package cn.milai.ib.container;

/**
 * 有生命周期（可启动、暂停、关闭等操作）的容器
 * 2020.01.16
 * @author milai
 */
public interface LifecycleContainer extends Container {
	
	/**
	 * 启动容器
	 */
	void start();

	/**
	 * 重置容器
	 */
	void reset();

	/**
	 * 关闭容器
	 */
	void close();

	/**
	 * 暂停或继续容器
	 */
	void pauseOrResume();
	
	/**
	 * 设置是否固定游戏角色，即是否暂停游戏角色的移动、碰撞检测
	 * 重绘不受影响但帧累计数不会增加
	 */
	void setPin(boolean pin);

}
