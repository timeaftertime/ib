package cn.milai.ib.role.property.base;

import cn.milai.ib.role.property.Rigidbody;

/**
 * {@link Rigidbody} 默认实现
 * @author milai
 * @date 2021.03.28
 */
public class BaseRigidbody extends BaseRoleProperty implements Rigidbody {

	private double mass = 1;
	private double forceX;
	private double forceY;
	private double resistance;

	private double extraForceX;
	private double extraForceY;

	@Override
	public double getMass() { return mass; }

	@Override
	public void setMass(double mass) {
		if (mass <= 0) {
			throw new IllegalArgumentException("质量必须大于 0");
		}
		this.mass = mass;
	}

	@Override
	public double getForceX() { return forceX; }

	@Override
	public double getForceY() { return forceY; }

	@Override
	public double addForceX(double force) {
		return this.forceX += force;
	}

	@Override
	public double addForceY(double force) {
		return this.forceY += force;
	}

	@Override
	public double addExtraForceX(double force) {
		return extraForceX = extraForceX + force;
	}

	@Override
	public double addExtraForceY(double force) {
		return extraForceY = extraForceY + force;
	}

	@Override
	public double getExtraForceX() { return extraForceX; }

	@Override
	public double getExtraForceY() { return extraForceY; }

	@Override
	public double getResistance() { return resistance; }

	@Override
	public void setResistance(double resistance) {
		if (resistance < 0) {
			throw new IllegalArgumentException("阻力必须大于等于 0");
		}
		this.resistance = resistance;
	}

}
