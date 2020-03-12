package cn.milai.ib.character;

import cn.milai.ib.character.property.Movable;
import cn.milai.ib.container.Container;

/**
 * 可移动的游戏对象
 * @author milai
 */
public abstract class MovableIBCharacter extends AbstractIBCharacter implements Movable {

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
