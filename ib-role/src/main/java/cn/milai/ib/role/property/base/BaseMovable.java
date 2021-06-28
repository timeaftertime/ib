package cn.milai.ib.role.property.base;

import cn.milai.ib.role.property.Movable;
import cn.milai.ib.role.property.Rigidbody;

/**
 * {@link Movable} 默认实现
 * @author milai
 * @date 2021.03.29
 */
public class BaseMovable extends BaseRoleProperty implements Movable {

	private double ratedSpeedX;
	private double ratedSpeedY;
	private double speedX;
	private double speedY;

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
		Rigidbody r = owner().getProperty(Rigidbody.class);
		if (r == null) {
			return;
		}
		// 加速度
		speedX += r.accX();
		speedY += r.accY();
		r.addForceX(-r.getForceX());
		r.addForceY(-r.getForceY());
		// 不超过额定速度
		if (Math.abs(speedX) > ratedSpeedX) {
			speedX = ratedSpeedX * Math.signum(speedX);
		}
		if (Math.abs(speedY) > ratedSpeedY) {
			speedY = ratedSpeedY * Math.signum(speedY);
		}
		// 额外加速度
		speedX += r.extraACCX();
		speedY += r.extraACCY();
		r.addExtraForceX(-r.getExtraForceX());
		r.addExtraForceY(-r.getExtraForceY());
		//  阻力加速度
		double deltaX = 0;
		double deltaY = speedY == 0 ? 0 : r.getResistance() / r.getMass();
		if (speedX != 0) {
			double alpha = Math.atan(Math.abs(speedY / speedX));
			deltaX = r.getResistance() * Math.cos(alpha) / r.getMass();
			deltaY = r.getResistance() * Math.sin(alpha) / r.getMass();
		}
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
