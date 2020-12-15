package cn.milai.ib.component;

import cn.milai.ib.IBObject;
import cn.milai.ib.Paintable;
import cn.milai.ib.container.ui.UIContainer;

/**
 * 用于交互的组件
 * @author milai
 * @date 2020.02.20
 */
public interface IBComponent extends IBObject {

	/**
	 * 获取所属 UI 容器
	 */
	UIContainer getContainer();

	@Override
	default int getZ() {
		return Paintable.DEFAULT_Z + 100;
	}
}
