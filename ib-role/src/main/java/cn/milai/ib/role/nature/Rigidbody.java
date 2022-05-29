package cn.milai.ib.role.nature;

import cn.milai.ib.actor.nature.Nature;

/**
 * 刚体，可受到物理作用的 {@link Nature}
 * @author milai
 * @date 2021.03.25
 */
public interface Rigidbody extends RoleNature {

	String NAME = "rigidbody";

	@Override
	default String name() {
		return NAME;
	}

	/**
	 * 获取物体质量
	 * @return
	 */
	double getMass();

	/**
	 * 设置质量
	 * @param mass
	 */
	void setMass(double mass);

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
		return getForceX() / getMass();
	}

	/**
	 * 获取 Y 方向加速度
	 * @return
	 */
	default double accY() {
		return getForceY() / getMass();
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
		return getExtraForceX() / getMass();
	}

	/**
	 * 获取 Y 方向 extra 力造成的加速度
	 * @return
	 */
	default double extraACCY() {
		return getExtraForceY() / getMass();
	}

	/**
	 * 获取所受阻力
	 * @return
	 */
	double getResistance();

	/**
	 * 设置所受阻力
	 * @param resistance
	 */
	void setResistance(double resistance);
}
