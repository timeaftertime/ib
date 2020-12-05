package cn.milai.ib;

/**
 * {@link Bounds } 的默认实现
 * @author milai
 * @date 2020.11.29
 */
public class BaseBounds implements Bounds {

	private int x;
	private int y;
	private int width;
	private int height;

	/**
	 * 构造以 (0, 0) 为左上角，宽度和高度为 0 的 {@link Bounds }
	 */
	public BaseBounds() {
		this(0, 0, 0, 0);
	}

	/**
	 * 构造以 (x, y) 为左上角，宽度和高度为指定值的 {@link Bounds }
	 */
	public BaseBounds(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public int getY() {
		return this.y;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public int getWidth() {
		return this.width;
	}

	@Override
	public void setWidth(int width) {
		this.width = width;
	}
}
