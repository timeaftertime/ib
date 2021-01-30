package cn.milai.ib.component.text;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import cn.milai.ib.IBObject;
import cn.milai.ib.component.PassCaculator;
import cn.milai.ib.container.Container;
import cn.milai.ib.container.ContainerEventListener;
import cn.milai.ib.util.ImageUtil;

/**
 * 显示多行文字、背景透明的窗口组件
 * @author milai
 * @date 2020.04.05
 */
public class TextLines extends AbstractTextComponent implements ContainerEventListener {

	private static final int PADDING = 5;

	private PassCaculator pass;
	BufferedImage img;

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
	public TextLines(int x, int y, Container container, List<String> lines, Color bgColor, long inFrame, long keepFrame,
		long outFrame) {
		super(x, y, container);
		container.addEventListener(this);
		pass = new PassCaculator(inFrame, keepFrame, outFrame);
		img = ImageUtil.newTextImage(lines, getTextFont(), getTextColor(), bgColor, PADDING, 0);
		setW(img.getWidth());
		setH(img.getHeight());
	}

	/**
	 * 创建一个多行文本
	 * @param x
	 * @param y
	 * @param container
	 * @param lines 需要显示的文本
	 * @param bgColor 背景颜色
	 * @param frame 整个显示过程的总帧数
	 */
	public TextLines(int x, int y, Container container, List<String> lines, Color bgColor, long frame) {
		this(x, y, container, lines, bgColor, frame / 3, frame / 3, frame / 3);
	}

	@Override
	protected double doubleProp(String key) {
		if (IBObject.P_WIDTH.equals(key) || IBObject.P_HEIGHT.equals(key)) {
			return 0;
		}
		return super.doubleProp(key);
	}

	@Override
	public void afterRefresh(Container container) {
		pass.refresh();
		if (pass.isEnd()) {
			container.removeEventListener(this);
			container.removeObject(this);
		}
	}

	@Override
	public BufferedImage getNowImage() {
		if (!pass.isKeep()) {
			ImageUtil.transparent(img, pass.getTransparency());
		}
		return img;
	}
}
