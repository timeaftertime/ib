package cn.milai.ib.control.text;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import cn.milai.ib.IBObject;
import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.listener.LifecycleListener;
import cn.milai.ib.control.PassCaculator;
import cn.milai.ib.graphics.Images;

/**
 * 显示多行文字、背景透明的窗口组件
 * @author milai
 * @date 2020.04.05
 */
public class TextLines extends AbstractTextControl implements LifecycleListener {

	private static final int PADDING = 5;

	private PassCaculator pass;
	private BufferedImage img;

	/**
	 * 创建一个多行文本
	 * @param x
	 * @param y
	 * @param container
	 * @param lines 需要显示的文本
	 * @param bgColor 背景颜色
	 * @param frame 整个显示过程的总帧数
	 */
	public TextLines(int x, int y, LifecycleContainer container, List<String> lines, Color bgColor, long frame) {
		this(x, y, container, lines, bgColor, frame / 3, frame / 3, frame / 3);
	}

	/**
	 * 创建一个多行文本
	 * @param x
	 * @param y
	 * @param container
	 * @param lines 需要显示的文本
	 * @param bgColor 背景颜色
	 * @param inFrame 渐入的持续帧数
	 * @param keepFrame 维持不变的持续帧数
	 * @param outFrame 渐出的持续帧数
	 */
	public TextLines(int x, int y, LifecycleContainer container, List<String> lines, Color bgColor, long inFrame,
		long keepFrame,
		long outFrame) {
		super(x, y, container);
		container.addLifecycleListener(this);
		pass = new PassCaculator(inFrame, keepFrame, outFrame, c -> {
			container.removeLifecycleListener(this);
			container.removeObject(this);
		});
		img = Images.newTextImage(lines, getTextFont(), getTextColor(), bgColor, PADDING, 0);
		setW(img.getWidth());
		setH(img.getHeight());
	}

	@Override
	public void onRefresh(LifecycleContainer container) {
		pass.refresh();
	}

	@Override
	public double doubleConf(String key) {
		if (IBObject.P_WIDTH.equals(key) || IBObject.P_HEIGHT.equals(key)) {
			return 0;
		}
		return super.doubleConf(key);
	}

	@Override
	public BufferedImage getNowImage() {
		if (!pass.isKeep()) {
			Images.transparent(img, pass.getTransparency());
		}
		return img;
	}
}
