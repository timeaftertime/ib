package cn.milai.ib.control.text;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import cn.milai.ib.config.ProtoConfiguarable;
import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.listener.LifecycleListener;
import cn.milai.ib.control.PassCaculator;
import cn.milai.ib.graphics.Images;
import cn.milai.ib.obj.BasePainter;
import cn.milai.ib.obj.property.Painter;

/**
 * 显示多行文字、背景透明的窗口组件
 * @author milai
 * @date 2020.04.05
 */
@ProtoConfiguarable
public class TextLines extends AbstractTextControl implements LifecycleListener {

	private static final int PADDING = 5;

	private PassCaculator pass;
	private BufferedImage img;
	private long inFrame;
	private long keepFrame;
	private long outFrame;

	/**
	 * 创建一个多行文本
	 * @param lines 需要显示的文本
	 * @param bgColor 背景颜色
	 * @param inFrame 渐入的持续帧数
	 * @param keepFrame 维持不变的持续帧数
	 * @param outFrame 渐出的持续帧数
	 */
	public TextLines(List<String> lines, Color bgColor, long inFrame, long keepFrame, long outFrame) {
		this.inFrame = inFrame;
		this.keepFrame = keepFrame;
		this.outFrame = outFrame;
		setW(img.getWidth());
		setH(img.getHeight());
		img = Images.newTextImage(
			lines, getTextProperty().getFont(), getTextProperty().getColor(), bgColor, PADDING, 0
		);
	}

	@Override
	public void initObject() {
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
	protected Painter initPainter() {
		return new BasePainter() {
			@Override
			public BufferedImage getNowImage() {
				if (!pass.isKeep()) {
					Images.transparent(img, pass.getTransparency());
				}
				return img;
			}
		};
	}
}
