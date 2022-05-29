package cn.milai.ib.actor.prop;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import cn.milai.ib.graphics.Images;
import cn.milai.ib.role.Role;

/**
 * 计数型生命显示器
 * @author milai
 * @date 2020.03.24
 */
public class LifeCounter extends LifeIndicator {

	public static Color LIFE_COLOR = Color.WHITE;

	public LifeCounter(Role target) {
		super(target);
	}

	@Override
	protected BufferedImage createImage() {
		BufferedImage image = Images.newImage(getIntW(), getIntH());
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(LIFE_COLOR);
		g2d.drawImage(getTargetImage(), 0, 0, getIntW() / 4, getIntH(), null);
		g2d.drawString("" + getTarget().getHealth().getHP(), getIntW() / 3, getIntH() / 2);
		return image;
	}

}
