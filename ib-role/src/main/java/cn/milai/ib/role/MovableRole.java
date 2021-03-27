package cn.milai.ib.role;

import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.role.property.Movable;

/**
 * 可移动的游戏对象
 * @author milai
 */
public abstract class MovableRole extends AbstractRole implements Movable {

	private double ratedSpeedX;
	private double ratedSpeedY;
	private double speedX;
	private double speedY;

	public MovableRole(double x, double y, LifecycleContainer container) {
		super(x, y, container);
		ratedSpeedX = initRatedSpeedX();
		ratedSpeedY = initRatedSpeedY();
		speedX = 0;
		speedY = 0;
	}

	protected double initRatedSpeedX() {
		return doubleProp(Movable.P_RATED_SPEED_X);
	}

	protected double initRatedSpeedY() {
		return doubleProp(Movable.P_RATED_SPEED_Y);
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
	public final void move() {
		beforeMove();
		setX(getX() + getSpeedX());
		setY(getY() + getSpeedY());
		afterMove();
	}

	@Override
	public double getSpeedX() { return speedX; }

	@Override
	public void setSpeedX(double speedX) { this.speedX = speedX; }

	@Override
	public double getSpeedY() { return speedY; }

	@Override
	public void setSpeedY(double speedY) { this.speedY = speedY; }

	/**
	 * 实际移动坐标前调用
	 */
	protected void beforeMove() {}

	/**
	 * 实际移动坐标后调用
	 */
	protected void afterMove() {}
}
