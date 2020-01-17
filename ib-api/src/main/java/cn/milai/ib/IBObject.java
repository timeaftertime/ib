package cn.milai.ib;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.container.Container;
import cn.milai.ib.property.Locable;
import cn.milai.ib.property.Paintable;

/**
 * 所有游戏对象的抽象基类
 *
 * @author milai
 */
public abstract class IBObject implements Paintable, Locable {

	private static final String P_WIDTH = "width";
	private static final String P_HEIGHT = "height";

	private Rectangle rect;
	private Container container;
	private ImageLoader imageLoader = ImageLoader.getContextImageLoader();

	/**
	 * 创建一个中心位置在 (x, y) 且以 Container 为容器的游戏对象 
	 * @param x
	 * @param y
	 * @param container
	 */
	public IBObject(int x, int y, Container container) {
		int width = proratedIntProp(P_WIDTH);
		int height = proratedIntProp(P_HEIGHT);
		x = x - width / 2;
		y = y - height / 2;
		this.rect = new Rectangle(x, y, width, height);
		this.container = container;
	}

	/**
	 * 获取对象的按比例参数缩放的 int 类型配置信息
	 * 
	 * @param prop
	 * @return
	 */
	protected int proratedIntProp(String key) {
		return SystemConf.prorate(SystemConf.getInt(getClass(), key));
	}

	/**
	 * 获取对象的 int 类型配置信息
	 * 
	 * @return
	 */
	protected int intProp(String key) {
		return SystemConf.getInt(getClass(), key);
	}

	/**
	 * 获取对象的 long 类型配置信息
	 * 
	 * @return
	 */
	protected long longProp(String key) {
		return SystemConf.getLong(getClass(), key);
	}

	/**
	 * 获取对象的 double 类型配置信息
	 * 
	 * @param key
	 * @return
	 */
	protected double doubleProp(String key) {
		return SystemConf.getDouble(getClass(), key);
	}

	protected String prop(String key) {
		return SystemConf.getStr(getClass(), key);
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

	@Override
	public int getHeight() {
		return (int) rect.getHeight();
	}

	public void setHeight(int height) {
		this.rect.setSize(getWidth(), height);
	}

	@Override
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
	public boolean isContactWith(IBObject gameObj) {
		return !this.rect.intersection(gameObj.rect).isEmpty();
	}

	@Override
	public double getCenterX() {
		return rect.getCenterX();
	}

	@Override
	public double getCenterY() {
		return rect.getCenterY();
	}

	public boolean containPoint(int x, int y) {
		return rect.contains(x, y);
	}

	@Override
	public void paintWith(Graphics g) {
		if (getImage() == null) {
			throw new IllegalStateException("image 尚未初始化");
		}
		g.drawImage(getImage(), getX(), getY(), getWidth(), getHeight(), null);
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
		ensureIn(0, getContainer().getWidth(), getContainer().getHeight() - getContainer().getContentHeight(), getContainer().getHeight());
	}

	protected void ensureIn(int minX, int maxX, int minY, int maxY) {
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

	/**
	 * 获取当前游戏对象的图片
	 * 
	 * @return
	 */
	public abstract Image getImage();

	/**
	 * 设置当前游戏对象的图片
	 * 
	 * @param img
	 */
	public abstract void setImage(Image img);

}
