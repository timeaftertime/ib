package cn.milai.ib.component;

import cn.milai.ib.container.Container;

/**
 * 显示 “重新开始”图片字样的按钮
 * 2019.11.30
 * @author milai
 */
public class RestartButton extends Button {

	/**
	 * 创建一个显示“重新开始“的按钮
	 * @param x
	 * @param y
	 * @param container
	 * @param afterPressed 按钮被点击时调用
	 */
	public RestartButton(int x, int y, Container container, Runnable afterPressed) {
		super(x, y, container, afterPressed);
	}

}
