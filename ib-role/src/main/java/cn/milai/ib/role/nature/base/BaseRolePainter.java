package cn.milai.ib.role.nature.base;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import cn.milai.ib.actor.nature.BasePainter;
import cn.milai.ib.actor.nature.Painter;
import cn.milai.ib.graphics.Images;
import cn.milai.ib.role.Role;
import cn.milai.ib.role.nature.RoleNature;

/**
 * {@link Role} 的 {@link Painter} 默认实现
 * @author milai
 * @date 2022.05.25
 */
public class BaseRolePainter extends BasePainter implements RoleNature {

	public BaseRolePainter(Role owner) {
		super(owner);
	}

	@Override
	public void paintWith(Graphics g) {
		Role r = owner();
		if (!r.getHealth().isAlive()) {
			return;
		}
		if (r.isFixedBox()) {
			super.paintWith(g);
			return;
		}
		BufferedImage img = getNowImage();
		if (img == null) {
			return;
		}
		Images.paint((Graphics2D) g, img, r.getIntX(), r.getIntY(), r.getIntW(), r.getIntH(), r.getDirection());
	}

	@Override
	public Role owner() {
		return (Role) super.owner();
	}

}
