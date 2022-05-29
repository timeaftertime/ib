package cn.milai.ib.role.nature.base;

import cn.milai.ib.actor.config.Configurable;
import cn.milai.ib.role.Role;
import cn.milai.ib.role.nature.Movable;
import cn.milai.ib.role.nature.Rigidbody;
import cn.milai.ib.role.nature.holder.AwareMovableHolder;

/**
 * {@link Movable} 默认实现
 * @author milai
 * @date 2021.03.29
 */
public class BaseMovable extends AbstractRoleNature implements Movable {

	private double ratedSpeedX;
	private double ratedSpeedY;
	private double speedX;
	private double speedY;

	public BaseMovable(Role owner) {
		super(owner);
		if (!(owner instanceof AwareMovableHolder)) {
			throw new IllegalArgumentException("持有者必须为 " + AwareMovableHolder.class);
		}
	}

	@Override
	public AwareMovableHolder owner() {
		return (AwareMovableHolder) super.owner();
	}

	@Override
	public double getRatedSpeedX() { return ratedSpeedX; }

	@Configurable
	@Override
	public void setRatedSpeedX(double ratedSpeedX) { this.ratedSpeedX = ratedSpeedX; }

	@Override
	public double getRatedSpeedY() { return ratedSpeedY; }

	@Configurable
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

	@Override
	public void onMove() {
		applyForce();
	}

	@Override
	public void afterMove() {
		owner().afterMove(this);
	}

	/**
	 * 开始计算移动速度前调用
	 */
	protected void beforeRefreshSpeeds() {
		owner().beforeRefreshSpeeds(this);
	}

	/**
	 * 完成移动速度计算后调用
	 */
	protected void afterRefreshSpeeds() {
		owner().afterRefreshSpeeds(this);
	}

	private void doRefreshSpeeds() {
		applyForce();
		applyResistance();
	}

	private void applyForce() {
		Rigidbody r = owner().getNature(Rigidbody.NAME);
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
	}

	private void applyResistance() {
		Rigidbody r = owner().getNature(Rigidbody.NAME);
		if (r == null) {
			return;
		}
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
	public String toString() {
		return "BaseMovable [rsx=" + ratedSpeedX + ", rsy=" + ratedSpeedY + ", sx=" + speedX + ", sy=" + speedY + "]";
	}

}
