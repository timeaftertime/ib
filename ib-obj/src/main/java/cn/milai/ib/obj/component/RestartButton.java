package cn.milai.ib.obj.component;

import java.awt.Image;

import cn.milai.ib.container.Container;
import cn.milai.ib.util.ImageUtil;

/**
 * 显示 “重新开始”图片字样的按钮
 *
 * 2019.11.30
 *
 * @author milai
 */
public class RestartButton extends IBComponent {

	private static final Image IMG = ImageUtil.loadImage(RestartButton.class.getResource("/img/component/restart_button.gif"));

	public RestartButton(int x, int y, Container container) {
		super(x, y, IMG, container);
	}

}
