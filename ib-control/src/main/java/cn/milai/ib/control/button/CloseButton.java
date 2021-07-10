package cn.milai.ib.control.button;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * 关闭 × 样式的按钮
 * @author milai
 * @date 2021.03.14
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CloseButton extends OpacityButton {

	public CloseButton(Runnable afterPressed) {
		super(afterPressed);
	}

	@Override
	public int getZ() { return Integer.MAX_VALUE; }

}
