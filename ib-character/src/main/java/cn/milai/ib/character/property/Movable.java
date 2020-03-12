package cn.milai.ib.character.property;

import cn.milai.ib.obj.IBCharacter;

/**
 * 可移动角色
 * @author milai
 * @date 2020.02.20
 */
public interface Movable extends IBCharacter {

	String P_SPEED_X = "speedX";
	String P_SPEED_Y = "speedY";
	
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

}
