package cn.milai.ib.actor.prop.button;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import cn.milai.ib.actor.Layers;
import cn.milai.ib.actor.prop.OpacityControl;

/**
 * 关闭 × 样式的按钮
 * @author milai
 * @date 2021.03.14
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CloseButton extends OpacityControl {

	public CloseButton(int opacity) {
		super(opacity);
	}

	@Override
	public int getZ() { return Layers.SUBTITLE.getZ(); }

}
