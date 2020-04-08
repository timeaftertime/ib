package cn.milai.ib.character.property;

import cn.milai.ib.character.IBCharacter;

/**
 * 可移动角色
 * @author milai
 * @date 2020.02.20
 */
public interface Movable extends IBCharacter {

	/**
	 * 属性 [初始额定 X 速度] 的 key
	 */
	String P_RATED_SPEED_X = "ratedSpeedX";

	/**
	 * 属性 [初始额定 Y 速度] 的 key
	 */
	String P_RATED_SPEED_Y = "ratedSpeedY";

	/**
	 * 使当前角色移动一次
	 */
	void move();

	/**
	 * 获取 X 方向的速度
	 * @return
	 */
	int getSpeedX();

	/**
	 * 设置 X 方向速度
	 * @param speedX
	 */
	void setSpeedX(int speedX);

	/**
	 * 获取 Y 方向速度
	 * @return
	 */
	int getSpeedY();

	/**
	 * 设置 Y 方向速度
	 * @param speedY
	 */
	void setSpeedY(int speedY);

	/**
	 * 获取 X 方向额定速度
	 * 即 X 方向可达的最大速度
	 * @return
	 */
	int getRatedSpeedX();

	/**
	 * 设置 X 方向额定速度
	 * @param ratedSpeedX
	 */
	void setRatedSpeedX(int ratedSpeedX);

	/**
	 * 获取 Y 方向额定速度
	 * 即 Y 方向可达的最大速度
	 * @return
	 */
	int getRatedSpeedY();

	/**
	 * 设置 Y 方向额定速度
	 * @param ratedSpeedY
	 */
	void setRatedSpeedY(int ratedSpeedY);

}
