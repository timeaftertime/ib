package cn.milai.ib.control.text;

import java.awt.Color;
import java.awt.image.BufferedImage;

import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.listener.LifecycleListener;
import cn.milai.ib.control.PassCaculator;
import cn.milai.ib.graphics.Images;

/**
 * 显示多行文字的 {@link Label}
 * @author milai
 * @date 2020.04.05
 */
public class TextLines extends Label implements LifecycleListener {

	private PassCaculator pass;

	private long inFrame;
	private long keepFrame;
	private long outFrame;

	private final Color DEFAULT_BG_COLOR = Color.BLACK;

	/**
	 * 创建一个多行文本
	 * @param inFrame 渐入的持续帧数
	 * @param keepFrame 维持不变的持续帧数
	 * @param outFrame 渐出的持续帧数
	 * @param lines 需要显示的文本
	 */
	public TextLines(long inFrame, long keepFrame, long outFrame, String... lines) {
		this.inFrame = inFrame;
		this.keepFrame = keepFrame;
		this.outFrame = outFrame;
		setBgColor(DEFAULT_BG_COLOR);
		setWrap(true);
		setText(String.join(Label.WRAP, lines));
	}

	@Override
	public void initItem() {
		LifecycleContainer c = (LifecycleContainer) container();
		c.addLifecycleListener(this);
		pass = new PassCaculator(inFrame, keepFrame, outFrame, callback -> {
			c.removeLifecycleListener(this);
			c.removeObject(this);
		});
	}

	@Override
	public void onRefresh(LifecycleContainer container) {
		pass.refresh();
	}

	@Override
	protected BufferedImage getLabelImg(BufferedImage img) {
		if (pass.isKeep()) {
			return img;
		}
		return Images.transparent(Images.copy(img), pass.getTransparency());
	}

}
