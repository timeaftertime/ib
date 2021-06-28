package cn.milai.ib.control.button;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * 最小化图标的 {@link Button}
 * @author milai
 * @date 2021.03.21
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MinimizeButton extends OpacityButton {

	public MinimizeButton(Runnable afterPressed) {
		super(afterPressed);
	}

}
