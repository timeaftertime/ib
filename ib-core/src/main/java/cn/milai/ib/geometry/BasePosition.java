package cn.milai.ib.geometry;

/**
 * {@link Position} 基础实现
 * @author milai
 * @date 2022.01.25
 */
public class BasePosition implements Position {

	private double x;
	private double y;

	public BasePosition() {
	}

	public BasePosition(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	public double getX() { return x; }

	@Override
	public void setX(double x) { this.x = x; }

	@Override
	public double getY() { return y; }

	@Override
	public void setY(double y) { this.y = y; }

	@Override
	public String toString() {
		return "BasePosition(" + x + ", " + y + ")";
	}

}
