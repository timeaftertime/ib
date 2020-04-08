package cn.milai.ib.character;

import cn.milai.ib.character.property.Movable;
import cn.milai.ib.container.Container;

/**
 * 可移动的游戏对象
 * @author milai
 */
public abstract class MovableIBCharacter extends AbstractIBCharacter implements Movable {

	private int ratedSpeedX;
	private int ratedSpeedY;
	private int speedX;
	private int speedY;

	public MovableIBCharacter(int x, int y, Container container) {
		super(x, y, container);
		ratedSpeedX = initRatedSpeedX();
		ratedSpeedY = initRatedSpeedY();
		speedX = 0;
		speedY = 0;
	}

	protected int initRatedSpeedX() {
		return proratedIntProp(Movable.P_RATED_SPEED_X);
	}

	protected int initRatedSpeedY() {
		return proratedIntProp(Movable.P_RATED_SPEED_Y);
	}

	@Override
	public int getRatedSpeedX() {
		return ratedSpeedX;
	}

	@Override
	public void setRatedSpeedX(int ratedSpeedX) {
		this.ratedSpeedX = ratedSpeedX;
	}

	@Override
	public int getRatedSpeedY() {
		return ratedSpeedY;
	}

	@Override
	public void setRatedSpeedY(int ratedSpeedY) {
		this.ratedSpeedY = ratedSpeedY;
	}

	@Override
	public final void move() {
		beforeMove();
		setX(getX() + getSpeedX());
		setY(getY() + getSpeedY());
		afterMove();
	}

	@Override
	public int getSpeedX() {
		return speedX;
	}

	@Override
	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	@Override
	public int getSpeedY() {
		return speedY;
	}

	@Override
	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	/**
	 * 实际移动坐标前调用
	 */
	protected void beforeMove() {

	}

	/**
	 * 实际移动坐标后调用
	 */
	protected void afterMove() {

	}
}
