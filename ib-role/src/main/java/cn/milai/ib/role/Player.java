package cn.milai.ib.role;

/**
 * 玩家控制的
 * @author milai
 * @date 2020.12.14
 */
public interface Player {

	/**
	 * 设置向上移动的状态
	 * @return 向上状态是否变化
	 */
	boolean setUp();

	/**
	 * 清除向上移动的状态
	 * @return 向下状态是否变化
	 */
	boolean clearUp();

	/**
	 * 是否设置向上状态
	 * @return
	 */
	boolean isUp();

	/**
	 * 设置向下移动的状态
	 * @return 向下状态是否变化
	 */
	boolean setDown();

	/**
	 * 清除向下移动的状态
	 * @return 向下状态是否变化
	 */
	boolean clearDown();

	/**
	 * 是否设置向下状态
	 * @return
	 */
	boolean isDown();

	/**
	 * 设置向左移动的状态
	 * @return 向左状态是否变化
	 */
	boolean setLeft();

	/**
	 * 清除向左移动的状态
	 * @return 向左状态是否变化
	 */
	boolean clearLeft();

	/**
	 * 是否设置向左状态
	 * @return
	 */
	boolean isLeft();

	/**
	 * 设置向右移动的状态
	 * @return 向右状态是否变化
	 */
	boolean setRight();

	/**
	 * 清除向右移动的状态
	 * @return 向右状态是否变化
	 */
	boolean clearRight();

	/**
	 * 是否设置向右状态
	 * @return
	 */
	boolean isRight();

	/**
	 * 设置 A 命令的状态
	 * @return A 命令状态是否变化
	 */
	boolean setA();

	/**
	 * 清除 A 命令的状态
	 * @return A 命令状态是否变化
	 */
	boolean clearA();

	/**
	 * 是否设置 A 状态
	 * @return
	 */
	boolean isA();

	/**
	 * 保存当前状态
	 * @param createNew 是否一定创建新状态
	 */
	void pushStatus(boolean createNew);

}
