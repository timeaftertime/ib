package cn.milai.ib.client.game.obj;

import java.awt.Rectangle;

import cn.milai.ib.client.game.form.GameForm;

public abstract class GameObject {

	private Rectangle rect;
	private GameForm container;

	public GameObject(int x, int y, int width, int height, GameForm container) {
		this.rect = new Rectangle(x, y, width, height);
		this.container = container;
	}

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

	public boolean isContactWith(GameObject gameObj) {
		return !this.rect.intersection(gameObj.rect).isEmpty();
	}

	public int getCenterX() {
		return (int) rect.getCenterX();
	}

	public int getCenterY() {
		return (int) rect.getCenterY();
	}

	public boolean containPoint(int x, int y) {
		return rect.contains(x, y);
	}

	public final GameForm getContainer() {
		return container;
	}

}
