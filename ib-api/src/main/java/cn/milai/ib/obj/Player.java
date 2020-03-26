package cn.milai.ib.obj;

import cn.milai.ib.container.listener.Command;

/**
 * 玩家角色
 * 2020.01.15
 * @author milai
 */
public interface Player extends IBCharacter, Controllable {

	/**
	 * 属性 [初始额定 X 速度] 的 key
	 */
	String P_RATED_SPEED_X = "ratedSpeedX";

	/**
	 * 属性 [初始额定 Y 速度] 的 key
	 */
	String P_RATED_SPEED_Y = "ratedSpeedY";

	/**
	 * 屬性 [最大额定 X 速度] 的 key
	 */
	String P_MAX_RATED_SPEED_X = "maxRatedSpeedX";

	/**
	 * 属性 [最大额定 Y 速度] 的 key
	 */
	String P_MAX_RATED_SPEED_Y = "maxRatedSpeedY";

	@Override
	default boolean onReceive(Command command) {
		switch (command) {
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
				setShooting();
				return false;
			}
			default: {
				return true;
			}
		}
	}

	@Override
	default boolean onCancel(Command command) {
		switch (command) {
			case UP : {
				clearUp();
				return false;
			}
			case DOWN : {
				clearDown();
				return false;
			}
			case LEFT : {
				clearLeft();
				return false;
			}
			case RIGHT : {
				clearRight();
				return false;
			}
			case A : {
				clearShooting();
				return false;
			}
			default: {
				return true;
			}
		}
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
	 * 设置向下移动的状态
	 */
	void setDown();

	/**
	 * 清除向下移动的状态
	 */
	void clearDown();

	/**
	 * 设置向左移动的状态
	 */
	void setLeft();

	/**
	 * 清除向左移动的状态
	 */
	void clearLeft();

	/**
	 * 设置向右移动的状态
	 */
	void setRight();

	/**
	 * 清除向右移动的状态
	 */
	void clearRight();

	/**
	 * 设置射击的状态
	 */
	void setShooting();

	/**
	 * 清除射击的状态
	 */
	void clearShooting();

	/**
	 * 获取额定 X 速度，即真正运动时的 X 速度
	 * @return
	 */
	int getRatedSpeedX();

	/**
	 * 设置额定 X 速度
	 * @see Player#getRatedSpeedX()
	 * @param speedX
	 */
	void setRatedSpeedX(int speedX);

	/**
	 * 获取额定 Y 速度，即真正运动时的 Y 速度
	 * @return
	 */
	int getRatedSpeedY();

	/**
	 * 设置额定Y 速度
	 * @see Player#getRatedSpeedY()
	 * @param speedY
	 */
	void setRatedSpeedY(int speedY);

	/**
	 * 保存当前状态
	 * @param b
	 */
	void pushStatus(boolean b);

}
