package cn.milai.ib.control;

import java.awt.image.BufferedImage;

import cn.milai.ib.item.BasePainter;
import cn.milai.ib.item.property.holder.PainterHolder;
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

	@Override
	protected void initItem() {
		this.preLife = target.getHealth().initHP();
		targetImage = target.getPainter().getNowImage();
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
