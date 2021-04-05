package cn.milai.ib.role.property.base;

import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.Movable;
import cn.milai.ib.role.property.Rigidbody;

/**
 * {@link Movable} 默认实现
 * @author milai
 * @date 2021.03.29
 */
public class BaseMovable extends BaseProperty implements Movable {

	private double ratedSpeedX;
	private double ratedSpeedY;
	private double speedX;
	private double speedY;

	public BaseMovable(Role role) {
		super(role);
		ratedSpeedX = initRatedSpeedX();
		ratedSpeedY = initRatedSpeedY();
		initSpeed();
	}

	protected void initSpeed() {
		speedX = 0;
		speedY = 0;
	}

	protected double initRatedSpeedX() {
		return getRole().doubleProp(Movable.P_RATED_SPEED_X);
	}

	protected double initRatedSpeedY() {
		return getRole().doubleProp(Movable.P_RATED_SPEED_Y);
	}

	@Override
	public double getRatedSpeedX() { return ratedSpeedX; }

	@Override
	public void setRatedSpeedX(double ratedSpeedX) { this.ratedSpeedX = ratedSpeedX; }

	@Override
	public double getRatedSpeedY() { return ratedSpeedY; }

	@Override
	public void setRatedSpeedY(double ratedSpeedY) { this.ratedSpeedY = ratedSpeedY; }

	@Override
	public double getSpeedX() { return speedX; }

	@Override
	public void setSpeedX(double speedX) { this.speedX = speedX; }

	@Override
	public double getSpeedY() { return speedY; }

	@Override
	public void setSpeedY(double speedY) { this.speedY = speedY; }

	@Override
	public final void beforeMove() {
		refreshSpeeds();
	}

	private void refreshSpeeds() {
		beforeRefreshSpeeds();
		doRefreshSpeeds();
		afterRefreshSpeeds();
	}

	private void doRefreshSpeeds() {
		if (!getRole().hasProperty(Rigidbody.class)) {
			return;
		}
		Rigidbody r = getRole().getProperty(Rigidbody.class);
		speedX += r.getACCX();
		speedY += r.getACCY();
		// 清除受力
		r.addForceX(-r.getForceX());
		r.addForceY(-r.getForceY());
		// 加速度
		double deltaX = 0;
		double deltaY = speedY == 0 ? 0 : r.getResistance() / r.mass();
		if (speedX != 0) {
			double alpha = Math.atan(Math.abs(speedY / speedX));
			deltaX = r.getResistance() * Math.cos(alpha) / r.mass();
			deltaY = r.getResistance() * Math.sin(alpha) / r.mass();
		}
		//  阻力加速度
		if (speedX > 0) {
			speedX = Math.max(0, speedX - deltaX);
		} else if (speedX < 0) {
			speedX = Math.min(0, speedX + deltaX);
		}
		if (speedY > 0) {
			speedY = Math.max(0, speedY - deltaY);
		} else if (speedY < 0) {
			speedY = Math.min(0, speedY + deltaY);
		}
		// 不超过额定速度
		if (Math.abs(speedX) > ratedSpeedX) {
			speedX = ratedSpeedX * (speedX > 0 ? 1 : -1);
		}
		if (Math.abs(speedY) > ratedSpeedY) {
			speedY = ratedSpeedY * (speedY > 0 ? 1 : -1);
		}
	}

	@Override
	public void onMove() {
		doRefreshSpeeds();
	}

	/**
	 * 开始计算移动速度前调用
	 */
	protected void beforeRefreshSpeeds() {}

	/**
	 * 完成移动速度计算后调用
	 */
	protected void afterRefreshSpeeds() {}

}
