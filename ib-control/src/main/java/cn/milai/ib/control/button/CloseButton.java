package cn.milai.ib.control.button;

import cn.milai.ib.container.Container;

/**
 * 关闭 × 样式的按钮
 * @author milai
 * @date 2021.03.14
 */
public class CloseButton extends OpacityButton {

	public CloseButton(int x, int y, Container container, Runnable afterPressed) {
		super(x, y, container, afterPressed);
	}

}
