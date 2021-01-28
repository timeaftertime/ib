package cn.milai.ib.mode;

/**
 * 游戏模式
 * @author milai
 * @date 2020.03.06
 */
public interface GameMode {

	/**
	 * 游戏模式名
	 * @return
	 */
	String name();

	/**
	 * 初始化游戏模式，在 start() 之前将被调用
	 * 由于所有 GameMode 都会在启动时实例化，所有 GameMode 的
	 * 构造方法应该尽量简单，而把初始操作放在 init 中
	 */
	default void init() {

	}

	/**
	 * 开始运行，该方法应立即返回
	 * 若有耗时操作，应该创建新线程运行
	 */
	void start();

}
