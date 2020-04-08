package cn.milai.ib.component;

import java.awt.image.BufferedImage;

import cn.milai.ib.character.IBCharacter;
import cn.milai.ib.container.Container;

/**
 * 生命显示器
 * @author milai
 * @date 2020.03.24
 */
public abstract class LifeIndicator extends AbstractComponent {

	private IBCharacter target;
	private int initLife;
	private int preLife;
	private BufferedImage targetImage;
	private BufferedImage image;

	public LifeIndicator(int x, int y, Container container, IBCharacter target) {
		super(x, y, container);
		this.target = target;
		this.initLife = target.getLife();
		this.preLife = this.initLife;
		targetImage = target.getNowImage();
	}

	@Override
	public BufferedImage getNowImage() {
		if (image == null || target.getLife() != preLife) {
			preLife = target.getLife();
			image = createImage();
		}
		return image;
	}

	/**
	 * 构造一张新图片
	 * @return
	 */
	protected abstract BufferedImage createImage();

	/**
	 * 获取初始生命值
	 * @return
	 */
	public int getInitLife() {
		return initLife;
	}

	/**
	 * 获取显示对象
	 * @return
	 */
	public IBCharacter getTarget() {
		return target;
	}

	/**
	 * 获取目标的图片
	 * @return
	 */
	protected BufferedImage getTargetImage() {
		return targetImage;
	}

}
