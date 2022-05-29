package cn.milai.ib.actor.prop.button;

import java.awt.Button;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import cn.milai.ib.actor.Layers;
import cn.milai.ib.actor.prop.OpacityControl;

/**
 * 最小化图标的 {@link Button}
 * @author milai
 * @date 2021.03.21
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MinimizeButton extends OpacityControl {

	public MinimizeButton(int opacity) {
		super(opacity);
	}

	@Override
	public int getZ() { return Layers.SUBTITLE.getZ(); }

}
