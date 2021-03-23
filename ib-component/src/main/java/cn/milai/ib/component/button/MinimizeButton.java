package cn.milai.ib.component.button;

import cn.milai.ib.container.Container;

/**
 * 最小化图标的 {@link Button}
 * @author milai
 * @date 2021.03.21
 */
public class MinimizeButton extends OpacityButton {

	public MinimizeButton(int x, int y, Container container, Runnable afterPressed) {
		super(x, y, container, afterPressed);
	}

}
