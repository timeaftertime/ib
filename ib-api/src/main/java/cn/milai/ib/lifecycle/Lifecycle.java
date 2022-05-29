package cn.milai.ib.lifecycle;

/**
 * 生命周期
 * @author milai
 * @date 2022.05.14
 */
public interface Lifecycle {

	/**
	 * 启动。多次调用将被忽略
	 */
	void start();

	/**
	 * 刷新
	 */
	void refresh();

	/**
	 * 返回是否已经调用 {@link #start()} 且没有调用 {@link #close()}
	 * @return
	 */
	boolean isRunning();

	/**
	 * 获取 {@link #refresh()} 调用次数
	 * @return
	 */
	long getFrame();

	/**
	 * 获取 {@link #reset()} 调用次数 + 1
	 * @return
	 */
	int getEpoch();

	/**
	 * 重置
	 */
	void reset();

	/**
	 * 获取关联的 {@link LifecycleLoop}
	 * @return
	 */
	LifecycleLoop loop();

	/**
	 * 下一次需要刷新的 {@link System#nanoTime()} 时间
	 * @return
	 */
	long nextRefreshTime();

	/**
	 * 获取 {@link Unsafe} 对象
	 * @return
	 */
	Unsafe unsafe();

	/**
	 * 取消对 {@link #loop()} 的注册并关闭。多次调用将忽略
	 * @return 是否真的做了关闭操作。
	 */
	boolean close();

	/**
	 * 容器是否已经关闭
	 * @return
	 */
	boolean isClosed();

	/**
	 * 仅供内部使用的操作集合
	 * @author milai
	 * @date 2022.04.16
	 */
	interface Unsafe {

		/**
		 * 注册到指定 {@link LifecycleLoop}
		 * @param lifecycleLoop
		 */
		void register(LifecycleLoop lifecycleLoop);

		/**
		 * 注销注册
		 */
		void unregister();

	}
}
