package cn.milai.ib.control;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import cn.milai.ib.container.Container;
import cn.milai.ib.role.Role;
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
	public static final int STRIP_WIDTH = 3;

	public BloodStrip(int x, int y, Container container, Role target) {
		super(x, y, container, target);
	}

	@Override
	public BufferedImage createImage() {
		int portraitSize = getIntH();
		int bloodY = portraitSize / 2;
		BufferedImage image = ImageUtil.newImage(getIntW(), getIntH());
		Graphics2D g2d = image.createGraphics();
		// 血条背景
		g2d.setColor(BACK_COLOR);
		g2d.setStroke(new BasicStroke(STRIP_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2d.drawLine(portraitSize, bloodY, getIntW(), bloodY);
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
				getIntW(),
				portraitSize + (int) (1.0 * (getIntW() - portraitSize) * nowLife / initLife)),
			bloodY);
		// 角色图片
		g2d.setClip(new Ellipse2D.Double(0, 0, portraitSize, portraitSize));
		g2d.drawOval(0, 0, portraitSize, portraitSize);
		g2d.drawImage(getTargetImage(), 0, 0, portraitSize, portraitSize, null);
		g2d.dispose();
		return image;
	}

}
