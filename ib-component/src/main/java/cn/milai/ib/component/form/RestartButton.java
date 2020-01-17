package cn.milai.ib.component.form;

import java.awt.Image;

import cn.milai.ib.container.Container;
import cn.milai.ib.interaction.form.IBFormComponent;
import cn.milai.ib.util.ImageUtil;

/**
 * 显示 “重新开始”图片字样的按钮
 *
 * 2019.11.30
 *
 * @author milai
 */
public class RestartButton extends IBFormComponent {

	private static final Image IMG = ImageUtil.loadImage(RestartButton.class.getResource("/img/component/restart_button.gif"));

	public RestartButton(int x, int y, Container container) {
		super(x, y, container);
	}

	@Override
	public Image getImage() {
		return IMG;
	}

	@Override
	public void setImage(Image img) {
		throw new UnsupportedOperationException();
	}

}
