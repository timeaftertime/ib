package cn.milai.ib.obj;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.container.Container;
import cn.milai.ib.loader.ImageLoader;

/**
 * 所有游戏对象的抽象基类
 *
 * @author milai
 */
public abstract class AbstractIBObject implements IBObject {

	private Rectangle rect;
	private Container container;

	private Image img;

	/**
	 * 创建一个中心位置在 (x, y) 且以 Container 为容器的游戏对象 
	 * @param x
	 * @param y
	 * @param container
	 */
	public AbstractIBObject(int x, int y, Container container) {
		// 最先设置 container 以边构造时使用
		this.container = container;
		int width = proratedIntProp(P_WIDTH);
		int height = proratedIntProp(P_HEIGHT);
		x = x - width / 2;
		y = y - height / 2;
		this.rect = new Rectangle(x, y, width, height);
	}

	/**
	 * 获取需要使用的配置文件对应的 Class 对象
	 * @return
	 */
	protected Class<?> getConfigClass() {
		return getClass();
	}

	/**
	 * 获取对象的按比例参数缩放的 int 类型配置信息
	 * 
	 * @param prop
	 * @return
	 */
	protected int proratedIntProp(String key) {
		return SystemConf.prorate(SystemConf.getInt(getConfigClass(), key));
	}

	/**
	 * 获取对象的 int 类型配置信息
	 * 
	 * @return
	 */
	protected int intProp(String key) {
		return SystemConf.getInt(getConfigClass(), key);
	}

	/**
	 * 获取对象的 long 类型配置信息
	 * 
	 * @return
	 */
	protected long longProp(String key) {
		return SystemConf.getLong(getConfigClass(), key);
	}

	/**
	 * 获取对象的 double 类型配置信息
	 * 
	 * @param key
	 * @return
	 */
	protected double doubleProp(String key) {
		return SystemConf.getDouble(getConfigClass(), key);
	}

	protected String prop(String key) {
		return SystemConf.getStr(getConfigClass(), key);
	}

	@Override
	public int getX() {
		return (int) rect.getX();
	}

	@Override
	public void setX(int x) {
		this.rect.setLocation(x, getY());
	}

	@Override
	public int getY() {
		return (int) rect.getY();
	}

	@Override
	public void setY(int y) {
		this.rect.setLocation(getX(), y);
	}

	@Override
	public int getHeight() {
		return (int) rect.getHeight();
	}

	@Override
	public void setHeight(int height) {
		this.rect.setSize(getWidth(), height);
	}

	@Override
	public int getWidth() {
		return (int) this.rect.getWidth();
	}

	@Override
	public void setWidth(int width) {
		this.rect.setSize(width, getHeight());
	}

	@Override
	public boolean intersects(IBObject IBObj) {
		return this.rect.intersects(IBObj.getX(), IBObj.getY(), IBObj.getWidth(), IBObj.getHeight());
	}

	@Override
	public double getCenterX() {
		return rect.getCenterX();
	}

	@Override
	public double getCenterY() {
		return rect.getCenterY();
	}

	@Override
	public boolean containsPoint(int x, int y) {
		return rect.contains(x, y);
	}

	@Override
	public void paintWith(Graphics g) {
		Image image = getImage();
		if (image == null) {
			throw new IllegalStateException("image 尚未初始化");
		}
		g.drawImage(image, getX(), getY(), getWidth(), getHeight(), null);
	}

	protected String getStatus() {
		return null;
	}

	@Override
	public Container getContainer() {
		return container;
	}

	@Override
	public void ensureInContainer() {
		ensureIn(0, getContainer().getWidth(), getContainer().getHeight() - getContainer().getContentHeight(),
			getContainer().getHeight());
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
	 * @return
	 */
	public Image getImage() {
		return img == null ? (img = ImageLoader.load(this.getConfigClass(), getStatus())) : img;
	}

	/**
	 * 设置当前游戏对象的图片
	 * @param img
	 */
	public void setImage(Image img) {
		this.img = img;
	}

}
