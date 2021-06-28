package cn.milai.ib.control;

import cn.milai.ib.obj.BaseObject;
import cn.milai.ib.obj.BasePainter;
import cn.milai.ib.obj.property.Painter;

/**
 * {@link Control} 的抽象基类
 * @author milai
 * @date 2020.02.20
 */
public class BaseControl extends BaseObject implements Control {

	public BaseControl() {
		setPainter(initPainter());
	}

	protected Painter initPainter() {
		return new BasePainter();
	}

	@Override
	public int getZ() { return super.getZ() + 100; }

}
