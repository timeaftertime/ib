package cn.milai.ib.component;

import cn.milai.ib.IBObject;
import cn.milai.ib.Paintable;

/**
 * 用于交互的组件
 * @author milai
 * @date 2020.02.20
 */
public interface IBComponent extends IBObject {

	@Override
	default int getPaintLayer() {
		return Paintable.GAME_COMPONENT_LAYER;
	}
}
