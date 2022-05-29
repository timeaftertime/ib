package cn.milai.ib.geometry;

/**
 * 矩形范围
 * 2019.11.29
 * @author milai
 */
public interface Bounds extends Position, Size {

	/**
	 * 获取 X 坐标四舍五入后的 int 值
	 * @return
	 */
	default int getIntX() { return (int) Math.round(getX()); }

	/**
	 * 获取 Y 坐标四舍五入后的 int 值
	 * @return
	 */
	default int getIntY() { return (int) Math.round(getY()); }

	/**
	 * 获取宽度四舍五入后的 int 值
	 * @return
	 */
	default int getIntW() { return (int) Math.round(getW()); }

	/**
	 * 获取高度四舍五入后的 int 值
	 * @return
	 */
	default int getIntH() { return (int) Math.round(getH()); }

	/**
	 * 重新设置边界
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	@SuppressWarnings("unchecked")
	default <T extends Bounds> T resetBounds(double x, double y, double w, double h) {
		setX(x);
		setY(y);
		setW(w);
		setH(h);
		return (T) this;
	}

	/**
	 * 重新设置大小
	 * @param w
	 * @param h
	 */
	@SuppressWarnings("unchecked")
	default <T extends Bounds> T resize(double w, double h) {
		setW(w);
		setH(h);
		return (T) this;
	}

	/**
	 * 中心 X 坐标
	 * @return
	 */
	default double centerX() {
		return getX() + getW() / 2;
	}

	/**
	 * 中心 Y 坐标
	 * @return
	 */
	default double centerY() {
		return getY() + getH() / 2;
	}

	/**
	 * 获取四个边界点，顺序为左上、左下、右下、右上点的坐标
	 * @return
	 */
	Position[] positions();

	/**
	 * 当前 {@link Bounds} 是否包含指定点
	 * @param x
	 * @param y
	 * @return
	 */
	default boolean contains(double x, double y) {
		return x >= getX() && x <= getX() + getW() && y >= getY() && y <= getY() + getH();
	}

	/**
	 * 设置 x, y 为指定值并返回原对象
	 * @param <T>
	 * @param x
	 * @param y
	 * @return
	 */
	@SuppressWarnings("unchecked")
	default <T extends Bounds> T xy(double x, double y) {
		setX(x);
		setY(y);
		return (T) this;
	}

	/**
	 * 设置中间 x, y 为指定值并返回原对象
	 * @param <T>
	 * @param x
	 * @param y
	 * @return
	 */
	@SuppressWarnings("unchecked")
	default <T extends Bounds> T centerXY(double x, double y) {
		setX(x - getW() / 2);
		setY(y - getH() / 2);
		return (T) this;
	}

}
