package cn.milai.ib;

import java.awt.image.BufferedImage;

import org.apache.commons.lang3.StringUtils;

import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.container.Container;
import cn.milai.ib.container.ui.Image;
import cn.milai.ib.loader.ImageLoader;

/**
 * 所有游戏对象的抽象基类
 * @author milai
 */
public abstract class AbstractIBObject extends BaseBounds implements IBObject {

	private Container container;
	private Class<? extends IBObject> configClass;
	private Image img;
	private String status;

	/**
	 * 创建一个中心位置在 (x, y) 且以 Container 为容器，使用 configClass 的配置文件的游戏对象
	 * 若 configClass 为 null，则通过 getClass() 获取
	 * @param x
	 * @param y
	 * @param container
	 * @param configClass
	 */
	public AbstractIBObject(int x, int y, Container container, Class<? extends IBObject> configClass) {
		// 最先设置 container 以便构造时使用
		this.container = container;
		this.configClass = configClass == null ? getClass() : configClass;
		int width = getInitWidth();
		int height = getInitHeight();
		x = x - width / 2;
		y = y - height / 2;
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}

	/**
	 * 获取初始宽度
	 * @return
	 */
	protected int getInitWidth() {
		return intProp(P_WIDTH);
	}

	/**
	 * 获取初始高度
	 * @return
	 */
	protected int getInitHeight() {
		return intProp(P_HEIGHT);
	}

	/**
	 * 创建一个中心位置在 (x, y) 且以 Container 为容器，使用 getClass() 的配置文件的游戏对象 
	 * @param x
	 * @param y
	 * @param container
	 */
	public AbstractIBObject(int x, int y, Container container) {
		this(x, y, container, null);
	}

	/**
	 * 获取对象的 int 类型配置信息
	 * @return
	 */
	protected int intProp(String key) {
		return SystemConf.intProp(configClass, key);
	}

	/**
	 * 获取对象的 long 类型配置信息
	 * @return
	 */
	protected long longProp(String key) {
		return SystemConf.longProp(configClass, key);
	}

	/**
	 * 获取对象的 double 类型配置信息
	 * @param key
	 * @return
	 */
	protected double doubleProp(String key) {
		return SystemConf.doubleProp(configClass, key);
	}

	/**
	 * 获取对象的 String 类型配置信息
	 * @param key
	 * @return
	 */
	protected String prop(String key) {
		return SystemConf.strProp(configClass, key);
	}

	@Override
	public double getCenterX() {
		return getX() + getWidth() / 2.0;
	}

	@Override
	public double getCenterY() {
		return getY() + getHeight() / 2.0;
	}

	@Override
	public Container getContainer() {
		return container;
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
	 * 获取当前游戏对象当前要被用于绘制的图片
	 * @return
	 */
	@Override
	public BufferedImage getNowImage() {
		return getImage().next();
	}

	/**
	 * 获取游戏对象的图片对象
	 * @return
	 */
	public Image getImage() {
		if (img == null) {
			loadImage();
		}
		return img;
	}

	/**
	 * 重新加载当前状态的图片
	 */
	protected void loadImage() {
		img = ImageLoader.load(configClass, getStatus());
	}

	/**
	 * 设置当前游戏对象的图片
	 * @param img
	 */
	public void setImage(Image img) {
		this.img = img;
	}

	/**
	 * 设置对象的状态
	 * @param status
	 */
	protected void setStatus(String status) {
		if (StringUtils.equals(this.status, status)) {
			return;
		}
		this.status = status;
		loadImage();
	}

	/**
	 * 获取当前对象状态
	 * @return
	 */
	protected String getStatus() {
		return status;
	}

}
