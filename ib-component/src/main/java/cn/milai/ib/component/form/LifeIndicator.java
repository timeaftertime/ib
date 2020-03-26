package cn.milai.ib.component.form;

import java.awt.Image;

import cn.milai.ib.container.Container;
import cn.milai.ib.obj.IBCharacter;
import cn.milai.ib.util.ImageUtil;

/**
 * 生命显示器
 * @author milai
 * @date 2020.03.24
 */
public abstract class LifeIndicator extends AbstractFormComponent {

	private IBCharacter target;
	private int initLife;
	private int preLife;
	private Image img;
	private Image targetImage;

	public LifeIndicator(int x, int y, Container container, IBCharacter target) {
		super(x, y, container);
		this.target = target;
		this.initLife = target.getLife();
		preLife = initLife;
		targetImage = ImageUtil.getJPGImage(target.getImage());
	}

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

	@Override
	public Image getImage() {
		if (img == null || preLife != target.getLife()) {
			preLife = target.getLife();
			img = createImage();
		}
		return img;
	}

	/**
	 * 获取目标的图片
	 * @return
	 */
	protected Image getTargetImage() {
		return targetImage;
	}

	/**
	 * 以当前状态创建一张新图片
	 * @return
	 */
	protected abstract Image createImage();

}
