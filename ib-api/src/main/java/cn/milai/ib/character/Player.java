package cn.milai.ib.character;

import cn.milai.ib.property.Alive;
import cn.milai.ib.property.Locable;
import cn.milai.ib.property.Paintable;

/**
 * 玩家
 * 2020.01.15
 * @author milai
 */
public interface Player extends Paintable, Alive, Locable {

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
