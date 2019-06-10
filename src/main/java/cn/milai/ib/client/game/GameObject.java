package cn.milai.ib.client.game;

import java.awt.Rectangle;

import cn.milai.ib.client.game.form.GameForm;
import cn.milai.ib.client.game.property.Alive;
import cn.milai.ib.client.game.property.Explosible;

public abstract class GameObject implements Alive {

	private Rectangle rect;

	public int getX() {
		return (int) rect.getX();
	}

	public void setX(int x) {
		this.rect.setLocation(x, getY());
	}

	public int getY() {
		return (int) rect.getY();
	}

	public void setY(int y) {
		this.rect.setLocation(getX(), y);
	}

	public int getHeight() {
		return (int) rect.getHeight();
	}

	public void setHeight(int height) {
		this.rect.setSize(getWidth(), height);
	}

	public int getWidth() {
		return (int) this.rect.getWidth();
	}

	public void setWidth(int width) {
		this.rect.setSize(width, getHeight());
	}

	public GameObject(int x, int y, int width, int height) {
		this.rect = new Rectangle(x, y, width, height);
	}

	public boolean isContactWith(GameObject gameObj) {
		return !this.rect.intersection(gameObj.rect).isEmpty();
	}

	public int getCenterX() {
		return (int) rect.getCenterX();
	}

	public int getCenterY() {
		return (int) rect.getCenterY();
	}
	
	public abstract GameForm getContainer();
	
	@Override
	public void onDead() {
		if(this instanceof Explosible)
			getContainer().addGameObject(new Bomb(getX(), getY(), getContainer()));
	}
	
}
