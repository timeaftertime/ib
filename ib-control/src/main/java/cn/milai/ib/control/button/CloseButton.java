package cn.milai.ib.control.button;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import cn.milai.ib.control.OpacityControl;
import cn.milai.ib.item.Layers;

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
