package cn.milai.ib.component.form;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import cn.milai.ib.container.Container;
import cn.milai.ib.obj.IBCharacter;
import cn.milai.ib.util.ImageUtil;

/**
 * 计数型生命显示器
 * @author milai
 * @date 2020.03.24
 */
public class LifeCounter extends LifeIndicator {

	public static Color LIFE_COLOR = Color.WHITE;

	public LifeCounter(int x, int y, Container container, IBCharacter target) {
		super(x, y, container, target);
	}

	@Override
	protected Image createImage() {
		BufferedImage image = ImageUtil.newImage(getWidth(), getHeight());
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(LIFE_COLOR);
		g2d.drawImage(getTargetImage(), 0, 0, getWidth() / 2, getHeight(), null);
		g2d.drawString("" + getTarget().getLife(), getWidth() * 3 / 4, getHeight() / 2);
		return image;
	}

}
