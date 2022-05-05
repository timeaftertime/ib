package cn.milai.ib.geometry.slot;

import cn.milai.ib.geometry.Position;

/**
 * 嵌入 {@link Position} 方式实现 {@link Position}
 * @author milai
 * @date 2022.04.17
 */
public interface PositionSlot extends Position {

	/**
	 * 获取持有的 {@link Position}
	 * @return
	 */
	Position position();

	@Override
	default double getX() { return position().getX(); }

	@Override
	default double getY() { return position().getY(); }

	@Override
	default void setX(double x) {
		position().setX(x);
	}

	@Override
	default void setY(double y) {
		position().setY(y);
	}
}
