package cn.milai.ib.container;

import cn.milai.ib.container.pluginable.BasePluginableContainer;
import cn.milai.ib.geometry.BaseBounds;
import cn.milai.ib.geometry.Bounds;

/**
 * {@link Stage} 的默认实现
 * @author milai
 * @date 2020.12.05
 */
public class BaseStage extends BasePluginableContainer implements Stage {

	private Bounds bounds = new BaseBounds();

	@Override
	public Bounds bounds() {
		return bounds;
	}
}
