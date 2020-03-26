package cn.milai.ib.component.form;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import cn.milai.ib.container.Container;
import cn.milai.ib.obj.IBCharacter;
import cn.milai.ib.util.ImageUtil;

/**
 * 血条型生命指示器
 * @author milai
 * @date 2020.03.24
 */
public class BloodStrip extends LifeIndicator {

	public static final Color INIT_COLOR = Color.GREEN;
	public static final Color DANGER_COLOR = Color.RED;
	public static final Color BACK_COLOR = Color.WHITE;
	public static final int STRIP_WIDTH = 2;

	public BloodStrip(int x, int y, Container container, IBCharacter target) {
		super(x, y, container, target);
	}

	@Override
	public Image createImage() {
		int portraitSize = getHeight();
		int bloodY = portraitSize / 2;
		BufferedImage image = ImageUtil.newImage(getWidth(), getHeight());
		Graphics2D g2d = image.createGraphics();
		// 血条背景
		g2d.setColor(BACK_COLOR);
		g2d.setStroke(new BasicStroke(STRIP_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2d.drawLine(portraitSize, bloodY, getWidth(), bloodY);
		// 生命值
		int nowLife = getTarget().getLife();
		int initLife = getInitLife();
		if (nowLife > initLife / 4) {
			g2d.setColor(INIT_COLOR);
		} else {
			g2d.setColor(DANGER_COLOR);
		}
		g2d.drawLine(portraitSize, bloodY,
			Integer.min(
				getWidth(),
				portraitSize + (int) (1.0 * (getWidth() - portraitSize) * nowLife / initLife)),
			bloodY);
		// 角色图片
		g2d.setClip(new Ellipse2D.Double(0, 0, portraitSize, portraitSize));
		g2d.drawOval(0, 0, portraitSize, portraitSize);
		g2d.drawImage(getTargetImage(), 0, 0, portraitSize, portraitSize, null);
		g2d.dispose();
		return image;
	}

}
