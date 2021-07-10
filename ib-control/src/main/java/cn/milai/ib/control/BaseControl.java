package cn.milai.ib.control;

import cn.milai.ib.item.BaseItem;
import cn.milai.ib.item.BasePainter;
import cn.milai.ib.item.property.Painter;

/**
 * {@link Control} 的抽象基类
 * @author milai
 * @date 2020.02.20
 */
public class BaseControl extends BaseItem implements Control {

	public BaseControl() {
		setPainter(createPainter());
	}

	protected Painter createPainter() {
		return new BasePainter();
	}

	@Override
	public int getZ() { return super.getZ() + 100; }

}
