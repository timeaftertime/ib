package cn.milai.ib.obj;

import cn.milai.ib.container.Container;
import cn.milai.ib.property.Movable;

/**
 * 可移动的游戏对象
 *
 * @author milai
 */
public abstract class MovableGameEntity extends GameEntity implements Movable {

	private int speedX;
	private int speedY;

	public MovableGameEntity(int x, int y, int width, int height, int speedX, int speedY, int life,
			Container container) {
		super(x, y, width, height, life, container);
		this.speedX = speedX;
		this.speedY = speedY;
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
