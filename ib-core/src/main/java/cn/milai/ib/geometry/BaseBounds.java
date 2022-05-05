package cn.milai.ib.geometry;

import cn.milai.ib.config.Configurable;

/**
 * {@link Bounds } 的默认实现
 * @author milai
 * @date 2020.11.29
 */
public class BaseBounds implements Bounds {
	
	private Position p = new BasePosition();
	private Size s = new BaseSize();

	/**
	 * 构造一个所有参数为 0 的 {@link BaseBounds}
	 */
	public BaseBounds() {
	}

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
	public double getX() { return p.getX(); }

	@Override
	public void setX(double x) {
		p.setX(x);
	}

	@Override
	public double getY() { return p.getY(); }

	@Override
	public void setY(double y) {
		p.setY(y);
	}

	@Override
	public double getW() { return s.getW(); }

	@Configurable
	@Override
	public void setW(double w) {
		s.setW(w);
	}

	@Override
	public double getH() { return s.getH(); }

	@Configurable
	@Override
	public void setH(double h) {
		s.setH(h);
	}

	@Override
	public Position[] positions() {
		double x = getX();
		double y = getY();
		double w = getW();
		double h = getH();
		return new Position[] {
			new BasePosition(x, y),
			new BasePosition(x, y + h),
			new BasePosition(x + w, y + h),
			new BasePosition(x + w, y)
		};
	}
	
	@Override
	public String toString() {
		return String.format("BaseBouds(%.5f, %.5f, %.5f, %.5f)", getX(), getY(), getW(), getH());
	}

}
