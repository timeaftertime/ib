package cn.milai.ib.obj.character;

import cn.milai.ib.container.Container;
import cn.milai.ib.property.Movable;

/**
 * 可移动的游戏对象
 *
 * @author milai
 */
public abstract class MovableIBCharacter extends IBCharacter implements Movable {

	private static final String P_SPEED_X = "speedX";
	private static final String P_SPEED_Y = "speedY";

	private int speedX;
	private int speedY;

	public MovableIBCharacter(int x, int y, Container container) {
		super(x, y, container);
		this.speedX = getInitSpeedX();
		this.speedY = getInitSpeedY();
	}

	public int getInitSpeedX() {
		return proratedIntProp(P_SPEED_X);
	}

	public int getInitSpeedY() {
		return proratedIntProp(P_SPEED_Y);
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	@Override
	public final void move() {
		beforeMove();
		setX(getX() + getSpeedX());
		setY(getY() + getSpeedY());
		afterMove();
	}

	protected void beforeMove() {
	}

	protected void afterMove() {
	}

}
