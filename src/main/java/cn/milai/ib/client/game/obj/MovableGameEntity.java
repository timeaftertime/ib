package cn.milai.ib.client.game.obj;

import java.awt.Image;

import cn.milai.ib.client.game.form.BattleForm;
import cn.milai.ib.client.game.obj.property.Movable;

public abstract class MovableGameEntity extends GameEntity implements Movable {

	private int speedX;
	private int speedY;

	public MovableGameEntity(int x, int y, int width, int height, int speedX, int speedY, int life, Image img,
			BattleForm container) {
		super(x, y, width, height, life, img, container);
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
