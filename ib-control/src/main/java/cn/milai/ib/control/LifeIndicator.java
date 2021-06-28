package cn.milai.ib.control;

import java.awt.image.BufferedImage;

import cn.milai.ib.obj.BasePainter;
import cn.milai.ib.obj.property.holder.PainterHolder;
import cn.milai.ib.role.Role;

/**
 * 生命显示器
 * @author milai
 * @date 2020.03.24
 */
public abstract class LifeIndicator extends BaseControl implements PainterHolder {

	private Role target;
	private int preLife;
	private BufferedImage targetImage;
	private BufferedImage image;

	public LifeIndicator(Role target) {
		this.target = target;
		this.preLife = target.getHealth().initHP();
		targetImage = target.getPainter().getNowImage();
		setPainter(new BasePainter() {
			@Override
			public BufferedImage getNowImage() {
				if (image == null || target.getHealth().getHP() != preLife) {
					preLife = target.getHealth().getHP();
					image = createImage();
				}
				return image;
			}
		});
	}

	/**
	 * 构造一张新图片
	 * @return
	 */
	protected abstract BufferedImage createImage();

	/**
	 * 获取显示对象
	 * @return
	 */
	public Role getTarget() { return target; }

	/**
	 * 获取目标的图片
	 * @return
	 */
	protected BufferedImage getTargetImage() { return targetImage; }

}
