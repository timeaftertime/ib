package cn.milai.ib.geometry;

import cn.milai.ib.config.Configurable;

/**
 * {@link Bounds } 的默认实现
 * @author milai
 * @date 2020.11.29
 */
public class BaseBounds implements Bounds {

	private double x;
	private double y;
	private double w;
	private double h;

	/**
	 * 构造一个所有参数为 0 的 {@link BaseBounds}
	 */
	public BaseBounds() {}

	/**
	 * 以指定 X坐标、Y坐标、宽度、高度构造一个 {@link BaseBounds}
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public BaseBounds(double x, double y, double w, double h) {
		resetBounds(x, y, w, h);
	}

	@Override
	public double getX() { return x; }

	@Override
	public int getIntX() { return (int) Math.round(getX()); }

	@Override
	public void setX(double x) { this.x = x; }

	@Override
	public double getY() { return y; }

	@Override
	public int getIntY() { return (int) Math.round(getY()); }

	@Override
	public void setY(double y) { this.y = y; }

	@Override
	public double getW() { return w; }

	@Override
	public int getIntW() { return (int) Math.round(getW()); }

	@Configurable
	@Override
	public void setW(double w) { this.w = w; }

	@Override
	public double getH() { return h; }

	@Override
	public int getIntH() { return (int) Math.round(getH()); }

	@Configurable
	@Override
	public void setH(double h) { this.h = h; }

	@Override
	public double centerX() {
		return getX() + getW() / 2;
	}

	@Override
	public double centerY() {
		return getY() + getH() / 2;
	}

}
