package cn.milai.ib.container.plugin.physics;

import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.Collider;
import cn.milai.ib.role.property.Movable;
import cn.milai.ib.role.property.Rigidbody;

/**
 * {@link Movable} 的搬运者
 * @author milai
 * @date 2021.04.07
 */
public class Mover implements Comparable<Mover> {

	/**
	 * 每次计算移动的最小距离
	 */
	private static final double STEP = 1.0;

	private Movable m;

	/**
	 * 上次调用 {@link #move()} 时的 speedX
	 */
	private double lastSpeedX;

	/**
	 * 上次调用 {@link #move()} 时的 speedY
	 */
	private double lastSpeedY;

	/**
	 * 已经移动的百分比
	 */
	private double fuelRatio;

	/**
	 * 在当前帧已经移动的距离
	 */
	private double movedX;

	/**
	 * 在当前帧已经移动的距离
	 */
	private double movedY;

	/**
	 * (速度不变化时)应该移动的 X 距离
	 */
	private double shouldMoveX;

	/**
	 * (速度不变化时)应该移动的 Y 距离
	 */
	private double shouldMoveY;

	public Mover(Movable m) {
		this.m = m;
		resetFuelRatio(1);
	}

	/**
	 * 移动一次
	 */
	public void move() {
		if (speedX() == 0 && speedY() == 0) {
			fuelRatio = 0;
			return;
		}
		Role r = m.owner();
		if (r.hasProperty(Rigidbody.class) && r.hasProperty(Collider.class)) {
			minMove();
			return;
		}
		r.setX(r.getX() + m.getSpeedX());
		r.setY(r.getY() + m.getSpeedY());
		fuelRatio = 0;
	}

	/**
	 * 移动一个最小距离
	 */
	private void minMove() {
		if (lastSpeedX != speedX() || lastSpeedY != speedY()) {
			resetFuelRatio(fuelRatio);
		}
		Role r = m.owner();
		double deltaX;
		double deltaY;
		if (Math.abs(speedX()) > Math.abs(speedY())) {
			deltaX = Math.min(shouldMoveX - movedX, STEP);
			double newMovedX = movedX + deltaX;
			deltaY = newMovedX / shouldMoveX * shouldMoveY - movedY;
			fuelRatio = (shouldMoveX - newMovedX) / shouldMoveX;
		} else {
			deltaY = Math.min(shouldMoveY - movedY, STEP);
			double newMovedY = movedY + deltaY;
			deltaX = newMovedY / shouldMoveY * shouldMoveX - movedX;
			fuelRatio = (shouldMoveY - newMovedY) / shouldMoveY;
		}
		r.setX(r.getX() + deltaX * (speedX() > 0 ? 1 : -1));
		r.setY(r.getY() + deltaY * (speedY() > 0 ? 1 : -1));
		movedX += deltaX;
		movedY += deltaY;
	}

	private double speedX() { return m.getSpeedX(); }

	private double speedY() { return m.getSpeedY(); }

	private void resetFuelRatio(double ratio) {
		fuelRatio = ratio;
		lastSpeedX = speedX();
		lastSpeedY = speedY();
		movedX = 0;
		movedY = 0;
		shouldMoveX = fuelRatio * Math.abs(speedX());
		shouldMoveY = fuelRatio * Math.abs(speedY());
	}

	/**
	 * 获取当前能量比率
	 * @return
	 */
	public double getFuelRatio() { return fuelRatio; }

	/**
	 * 获取持有的 {@link Movable}
	 * @return
	 */
	public Movable getMovable() { return m; }

	@Override
	public int compareTo(Mover o) {
		if (fuelRatio == o.fuelRatio) {
			return 0;
		}
		return fuelRatio > o.fuelRatio ? -1 : 1;
	}

	@Override
	public String toString() {
		return String.format(
			"Mover(%s) fuelRatio=%f, movedX=%f, movedY=%f", m.owner().getClass().getName(), fuelRatio, movedX, movedY
		);
	}

}
