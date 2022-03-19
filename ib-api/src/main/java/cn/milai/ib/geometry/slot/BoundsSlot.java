package cn.milai.ib.geometry.slot;

import cn.milai.ib.geometry.Bounds;
import cn.milai.ib.geometry.Position;

/**
 * 嵌入 {@link Bounds} 方式实现 {@link Bounds} 的接口 
 * @author milai
 * @date 2022.03.03
 */
public interface BoundsSlot extends Bounds {

	/**
	 * 获取持有的 {@link Bounds}
	 * @return
	 */
	Bounds bounds();

	@Override
	default double getW() { return bounds().getW(); }

	@Override
	default void setW(double w) {
		bounds().setW(w);
	}

	@Override
	default double getH() { return bounds().getH(); }

	@Override
	default void setH(double h) {
		bounds().setH(h);
	}

	@Override
	default double getX() { return bounds().getX(); }

	@Override
	default int getIntX() { return bounds().getIntX(); }

	@Override
	default void setX(double x) {
		bounds().setX(x);
	}

	@Override
	default double getY() { return bounds().getY(); }

	@Override
	default int getIntY() { return bounds().getIntY(); }

	@Override
	default void setY(double y) {
		bounds().setY(y);
	}

	@Override
	default double centerX() {
		return bounds().centerX();
	}

	@Override
	default double centerY() {
		return bounds().centerY();
	}

	@Override
	default Position[] positions() {
		return bounds().positions();
	}
}
