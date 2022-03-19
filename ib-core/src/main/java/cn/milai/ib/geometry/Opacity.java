package cn.milai.ib.geometry;

/**
 * 不透明度
 */
public class Opacity {

	private Opacity() {
	}

	/**
	 * 完全不透明
	 */
	public static final int MAX = 255;

	/**
	 * 完全不透明
	 */
	public static final double D_MAX = 1.0;

	/**
	 * 完全透明
	 */
	public static final int MIN = 0;

	/**
	 * 完全透明
	 */
	public static final double D_MIN = 0.0;

	/**
	 * 判断给定不透明度是否合法
	 * @param opacity
	 * @return
	 */
	public static boolean isValid(int opacity) {
		return opacity >= MIN && opacity <= MAX;
	}

	/**
	 * 判断给定不透明度是否合法
	 * @param opacity
	 * @return
	 */
	public static boolean isValid(double opacity) {
		return opacity >= D_MIN && opacity <= D_MAX;
	}

}
