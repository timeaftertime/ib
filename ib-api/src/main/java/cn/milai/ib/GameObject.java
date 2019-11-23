package cn.milai.ib;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import cn.milai.ib.container.Container;
import cn.milai.ib.property.HasLocation;
import cn.milai.ib.property.Paintable;

/**
 * 所有游戏对象的抽象基类
 *
 * @author milai
 */
public abstract class GameObject implements Paintable, HasLocation {

	private Rectangle rect;
	private Container container;
	private Image img;
	private ImageLoader imageLoader = ImageLoader.getContextImageLoader();

	public GameObject(int x, int y, int width, int height, Container container) {
		this.rect = new Rectangle(x, y, width, height);
		this.container = container;
	}

	@Override
	public int getX() {
		return (int) rect.getX();
	}

	public void setX(int x) {
		this.rect.setLocation(x, getY());
	}

	@Override
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

	/**
	 * 是否与 gameObj 接触
	 * @param gameObj
	 * @return
	 */
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

	@Override
	public void paintWith(Graphics g) {
		g.drawImage(getImage(), getX(), getY(), getWidth(), getHeight(), null);
	}

	public void setImage(Image img) {
		this.img = img;
	}

	public Image getImage() {
		return img == null ? (img = imageLoader.loadImage(this.getClass(), getStatus())) : img;
	}

	protected String getStatus() {
		return null;
	}

	public ImageLoader getImageLoader() {
		return imageLoader;
	}

	public void setImageLoader(ImageLoader imageLoader) {
		this.imageLoader = imageLoader;
	}

	public Container getContainer() {
		return container;
	}

	public void ensureInContainer() {
		ensureIn(0, getContainer().getWidth(), getContainer().getHeight() - getContainer().getContentHeight(),
				getContainer().getHeight());
	}

	public void ensureIn(int minX, int maxX, int minY, int maxY) {
		if (getX() < minX) {
			setX(minX);
		}
		if (getY() < minY) {
			setY(minY);
		}
		if (getX() + getWidth() > maxX) {
			setX(maxX - getWidth());
		}
		if (getY() + getHeight() > maxY) {
			setY(maxY - getHeight());
		}
	}

}
