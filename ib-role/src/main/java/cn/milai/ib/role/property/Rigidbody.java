package cn.milai.ib.role.property;

/**
 * 刚体，可受到物理作用的 {@link Property}
 * @author milai
 * @date 2021.03.25
 */
public interface Rigidbody extends Property {

	/**
	 * 质量
	 */
	String P_MASS = "mass";

	/**
	 * X 方向力大小
	 */
	String P_FORCE_X = "forceX";

	/**
	 * Y 方向力大小
	 */
	String P_FORCE_Y = "forceY";

	/**
	 * 能突破 {@link Movable#P_RATED_SPEED_X} 的力
	 */
	String P_EXTRA_FORCE_X = "extraForceX";

	/**
	 * 能突破 {@link Movable#P_RATED_SPEED_Y} 的力
	 */
	String P_EXTRA_FORCE_Y = "extraForceY";

	/**
	 * 阻力大小
	 */
	String P_RESISTANCE = "resistance";

	/**
	 * 获取持有者的 {@link #P_FORCE_X} 配置
	 * @return
	 */
	default double confForceX() {
		return getRole().doubleConf(P_FORCE_X);
	}

	/**
	 * 获取持有者的 {@link #P_FORCE_Y} 配置
	 * @return
	 */
	default double confForceY() {
		return getRole().doubleConf(P_FORCE_Y);
	}

	/**
	 * 获取持有者的 {@link #P_MASS} 配置
	 * @return
	 */
	default double confMass() {
		return getRole().doubleConf(P_MASS);
	}

	/**
	 * 获取物体质量
	 * @return
	 */
	double mass();

	/**
	 * 获取 X 方向的受力(不包括阻力)
	 * @return
	 */
	double getForceX();

	/**
	 * 获取 Y 方向的受力(不包括阻力)
	 * @return
	 */
	double getForceY();

	/**
	 * 给 X 方向增加一个力，返回增加后 X 方向的合力
	 * @param force
	 * @return
	 */
	double addForceX(double force);

	/**
	 * 给 Y 方向增加一个力，返回增加后 Y 方向的合力
	 * @param force
	 * @return
	 */
	double addForceY(double force);

	/**
	 * 获取 X 方向加速度
	 * @return
	 */
	default double accX() {
		return getForceX() / mass();
	}

	/**
	 * 获取 Y 方向加速度
	 * @return
	 */
	default double accY() {
		return getForceY() / mass();
	}

	/**
	 * 给 X 方向增加额外力，返回增加后 X 方向额外力
	 * @param force
	 * @return
	 */
	double addExtraForceX(double force);

	/**
	 * 给 Y 方向增加额外力，返回增加后 Y 方向额外力
	 * @param force
	 * @return
	 */
	double addExtraForceY(double force);

	/**
	 * 获取 X 方向额外力
	 * @return
	 */
	double getExtraForceX();

	/**
	 * 获取 Y 方向额外力
	 * @return
	 */
	double getExtraForceY();

	/**
	 * 获取 X 方向 extra 力造成的加速度
	 * @return
	 */
	default double extraACCX() {
		return getExtraForceX() / mass();
	}

	/**
	 * 获取 Y 方向 extra 力造成的加速度
	 * @return
	 */
	default double extraACCY() {
		return getExtraForceY() / mass();
	}

	/**
	 * 获取所受阻力
	 * @return
	 */
	default double getResistance() { return getRole().doubleConf(P_RESISTANCE); }
}
