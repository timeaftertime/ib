package cn.milai.ib.item.property.holder;

import cn.milai.ib.item.Item;
import cn.milai.ib.item.property.Painter;

/**
 * {@link Painter} 持有者
 * @author milai
 * @date 2021.06.25
 */
public interface PainterHolder extends Item {

	/**
	 * 获取持有的 {@link Painter}，若返回 null 表示不需要显示
	 * @return
	 */
	default Painter getPainter() { return getProperty(Painter.class); }

	/**
	 * 设置持有的 {@link Painter}
	 * @param p
	 */
	default void setPainter(Painter p) {
		putProperty(Painter.class, p);
	}

}
