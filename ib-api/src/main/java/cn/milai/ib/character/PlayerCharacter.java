package cn.milai.ib.character;

import cn.milai.ib.container.control.CommandType;
import cn.milai.ib.container.control.Controllable;

/**
 * 玩家角色
 * 2020.01.15
 * @author milai
 */
public interface PlayerCharacter extends IBCharacter, Controllable {

	@Override
	default boolean exec(CommandType type) {
		switch (type) {
			case UP : {
				setUp();
				return false;
			}
			case DOWN : {
				setDown();
				return false;
			}
			case LEFT : {
				setLeft();
				return false;
			}
			case RIGHT : {
				setRight();
				return false;
			}
			case A : {
				setA();
				return false;
			}
			case U_UP : {
				clearUp();
				return false;
			}
			case U_DOWN : {
				clearDown();
				return false;
			}
			case U_LEFT : {
				clearLeft();
				return false;
			}
			case U_RIGHT : {
				clearRight();
				return false;
			}
			case U_A : {
				clearA();
				return false;
			}
			default: {
				return true;
			}
		}
	}

	@Override
	default int getCamp() {
		return Camp.PLAYER;
	}

	/**
	 * 设置向上移动的状态
	 */
	void setUp();

	/**
	 * 清除向上移动的状态
	 */
	void clearUp();

	/**
	 * 是否设置向上状态
	 * @return
	 */
	boolean isUp();

	/**
	 * 设置向下移动的状态
	 */
	void setDown();

	/**
	 * 清除向下移动的状态
	 */
	void clearDown();

	/**
	 * 是否设置向下状态
	 * @return
	 */
	boolean isDown();

	/**
	 * 设置向左移动的状态
	 */
	void setLeft();

	/**
	 * 清除向左移动的状态
	 */
	void clearLeft();

	/**
	 * 是否设置向左状态
	 * @return
	 */
	boolean isLeft();

	/**
	 * 设置向右移动的状态
	 */
	void setRight();

	/**
	 * 清除向右移动的状态
	 */
	void clearRight();

	/**
	 * 是否设置向右状态
	 * @return
	 */
	boolean isRight();

	/**
	 * 设置 A 命令的状态
	 */
	void setA();

	/**
	 * 清除 A 命令的状态
	 */
	void clearA();

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
