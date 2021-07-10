package cn.milai.ib.control.text;

import java.awt.Color;
import java.awt.image.BufferedImage;

import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.listener.LifecycleListener;
import cn.milai.ib.control.PassCaculator;
import cn.milai.ib.graphics.Images;
import cn.milai.ib.item.BasePainter;
import cn.milai.ib.item.property.Painter;

/**
 * 显示多行文字、背景透明的窗口组件
 * @author milai
 * @date 2020.04.05
 */
public class TextLines extends AbstractTextControl implements LifecycleListener {

	private static final int PADDING = 5;

	private PassCaculator pass;
	private BufferedImage img;
	private long inFrame;
	private long keepFrame;
	private long outFrame;
	private Color bgColor = Color.BLACK;

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
		img = Images.newTextImage(
			getTextProperty().getFont(), getTextProperty().getColor(), bgColor, PADDING, 0, lines
		);
		setW(img.getWidth());
		setH(img.getHeight());
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
	protected Painter createPainter() {
		return new BasePainter() {
			@Override
			public BufferedImage getNowImage() {
				BufferedImage i = Images.copy(img);
				if (!pass.isKeep()) {
					Images.transparent(i, pass.getTransparency());
				}
				return i;
			}
		};
	}
}
