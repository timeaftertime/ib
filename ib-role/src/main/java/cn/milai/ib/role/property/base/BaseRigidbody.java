package cn.milai.ib.role.property.base;

import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.Rigidbody;

/**
 * {@link Rigidbody} 默认实现
 * @author milai
 * @date 2021.03.28
 */
public class BaseRigidbody extends BaseProperty implements Rigidbody {

	private double mass;
	private double forceX;
	private double forceY;

	public BaseRigidbody(Role role) {
		super(role);
		this.mass = confMass();
		if (mass <= 0) {
			throw new IllegalArgumentException("mass 必须大于 0");
		}
	}

	@Override
	public double mass() {
		return mass;
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

}
