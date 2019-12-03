package cn.milai.ib.obj.component;

import java.awt.Image;

import cn.milai.ib.container.Container;
import cn.milai.ib.util.ImageUtil;

/**
 * GameOver 字样的图片标签
 *
 * 2019.11.30
 *
 * @author milai
 */
public class GameOverLabel extends IBComponent {

	private static final Image IMG = ImageUtil.loadImage(GameOverLabel.class.getResource("/img/component/game_over_label.gif"));

	public GameOverLabel(int x, int y, Container container) {
		super(x, y, IMG, container);
	}

}
