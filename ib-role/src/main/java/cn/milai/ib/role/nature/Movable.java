package cn.milai.ib.role.nature;

/**
 * 可移动
 * @author milai
 * @date 2021.03.29
 */
public interface Movable extends RoleNature {

	String NAME = "movable";

	@Override
	default String name() {
		return NAME;
	}

	/**
	 * 每次移动后调用
	 */
	default void onMove() {
	}

	/**
	 * 开始移动前调用
	 */
	default void beforeMove() {
	}

	/**
	 * 结束移动后调用
	 */
	default void afterMove() {
	}

	/**
	 * 获取 X 方向的速度
	 * @return
	 */
	double getSpeedX();

	/**
	 * 设置 X 方向速度
	 * @param speedX
	 */
	void setSpeedX(double speedX);

	/**
	 * 获取 Y 方向速度
	 * @return
	 */
	double getSpeedY();

	/**
	 * 设置 Y 方向速度
	 * @param speedY
	 */
	void setSpeedY(double speedY);

	/**
	 * 获取 X 方向额定速度
	 * 即 X 方向可达的最大速度
	 * @return
	 */
	double getRatedSpeedX();

	/**
	 * 设置 X 方向额定速度
	 * @param ratedSpeedX
	 */
	void setRatedSpeedX(double ratedSpeedX);

	/**
	 * 获取 Y 方向额定速度
	 * 即 Y 方向可达的最大速度
	 * @return
	 */
	double getRatedSpeedY();

	/**
	 * 设置 Y 方向额定速度
	 * @param ratedSpeedY
	 */
	void setRatedSpeedY(double ratedSpeedY);

}
