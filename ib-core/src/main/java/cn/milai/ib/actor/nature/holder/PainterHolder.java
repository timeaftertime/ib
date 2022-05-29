package cn.milai.ib.actor.nature.holder;

import cn.milai.ib.actor.Actor;
import cn.milai.ib.actor.nature.Painter;

/**
 * {@link Painter} 持有者
 * @author milai
 * @date 2021.06.25
 */
public interface PainterHolder extends Actor {

	/**
	 * 获取持有的 {@link Painter}
	 * @return
	 */
	default Painter getPainter() { return getNature(Painter.NAME); }

	/**
	 * 设置 {@link Painter}
	 * @param p
	 */
	default void setPainter(Painter p) {
		putNature(p);
	}

}
