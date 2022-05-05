package cn.milai.ib.geometry;

/**
 * 尺寸
 * @author milai
 * @date 2022.01.23
 */
public interface Size {

	/**
	 * 获取宽度
	 * @return
	 */
	double getW();

	/**
	 * 设置宽度
	 * @param w
	 */
	default void setW(double w) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 获取高度
	 * @return
	 */
	double getH();

	/**
	 * 设置高度
	 * @param h
	 */
	default void setH(double h) {
		throw new UnsupportedOperationException();
	}

}
