package cn.milai.ib.role.property.base;

import java.awt.Graphics;
import java.awt.Graphics2D;

import cn.milai.ib.graphics.Images;
import cn.milai.ib.item.BasePainter;
import cn.milai.ib.item.property.Painter;
import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.Rotatable;

/**
 * {@link Rotatable} 默认实现
 * @author milai
 * @date 2021.04.05
 */
public class BaseRotatable extends BaseRoleProperty implements Rotatable {

	@Override
	public void initRoleProperty() {
		Role r = owner();
		Painter painter = r.getProperty(Painter.class);
		BasePainter rotatedPainter = new BasePainter() {
			@Override
			public void paintWith(Graphics g) {
				Images.paint(
					(Graphics2D) g, painter.getNowImage(),
					r.getIntX(), r.getIntY(), r.getIntW(), r.getIntH(),
					r.getDirection()
				);
			}
		};
		rotatedPainter.init(r);
		r.putProperty(Painter.class, rotatedPainter);
	}

}
