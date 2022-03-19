package cn.milai.ib.geometry;

/**
 * {@link Size} 默认实现
 * @author milai
 * @date 2022.01.25
 */
public class BaseSize implements Size {

	private double w;
	private double h;

	public BaseSize() {
	}

	public BaseSize(double w, double h) {
		this.w = w;
		this.h = h;
	}

	@Override
	public double getW() { return w; }

	@Override
	public void setW(double w) { this.w = w; }

	@Override
	public double getH() { return h; }

	@Override
	public void setH(double h) { this.h = h; }

	@Override
	public String toString() {
		return "BaseSize(" + w + ", " + h + ")";
	}

}
