package cn.milai.ib.control;

import cn.milai.ib.IBObject;
import cn.milai.ib.Paintable;

/**
 * 控件
 * @author milai
 * @date 2020.02.20
 */
public interface Control extends IBObject {
	
	@Override
	default int getZ() { return Paintable.DEFAULT_Z + 100; }
}
