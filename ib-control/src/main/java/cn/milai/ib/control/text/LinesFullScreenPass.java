package cn.milai.ib.control.text;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.listener.LifecycleListener;
import cn.milai.ib.control.PassCaculator;
import cn.milai.ib.graphics.Texts;
import cn.milai.ib.graphics.Images;

/**
 * 全屏显示多行文字渐入、渐出的组件
 * @author milai
 * @date 2020.02.21
 */
public class LinesFullScreenPass extends AbstractTextControl implements LifecycleListener {

	private PassCaculator pass;
	private BufferedImage img;
	private List<String> lines;
	private int lineInterval;

	/**
	 * 创建一个全屏、背景为黑色、文字行距为 lineInterval 、文字为 lines 、渐入渐出的组件
	 * @param durationFrames
	 * @param lines
	 * @param lineInterval
	 * @param c
	 */
	public LinesFullScreenPass(long durationFrames, List<String> lines, int lineInterval, LifecycleContainer c) {
		this(durationFrames / 3, durationFrames / 3, durationFrames / 3, lines, lineInterval, c);
	}

	/**
	 * 创建一个全屏、背景为黑色、文字行距为 lineInterval 、文字为 lines 、渐入渐出的组件
	 * @param inFrame 渐入持续的帧数
	 * @param keepFrame 不变持续的帧数
	 * @param outFrame 渐出持续的帧数
	 * @param lines
	 * @param lineInterval
	 * @param container
	 */
	public LinesFullScreenPass(long inFrame, long keepFrame, long outFrame, List<String> lines,
		int lineInterval, LifecycleContainer container) {
		super(0, 0, container);
		pass = new PassCaculator(inFrame, keepFrame, outFrame, p -> {
			container.removeLifecycleListener(this);
			container.removeObject(this);
		});
		this.lines = lines;
		this.lineInterval = lineInterval;
		container.addLifecycleListener(this);
	}

	@Override
	public void afterRefresh(LifecycleContainer container) {
		pass.refresh();
	}

	@Override
	public BufferedImage getNowImage() {
		if (!pass.isKeep()) {
			img = createImage();
		}
		return img;
	}

	private BufferedImage createImage() {
		BufferedImage backgroud = Images.newImage(Color.BLACK, getIntW(), getIntH());
		BufferedImage textLayer = Images.newImage(getIntW(), getIntH());
		Graphics g = Images.createGraphics(
			textLayer,
			1.0f * pass.getTransparency() / PassCaculator.MAX_TRANSPARENCY
		);
		g.setFont(getTextFont());
		g.setColor(getTextColor());
		int lineHeight = Texts.getTextHeight(g);
		int totalHeight = lines.size() * lineHeight;
		int nowY = (getContainer().getH() / 2) - (totalHeight / 2);
		for (String line : lines) {
			int lineWidth = Texts.getTextWidth(line, g);
			g.drawString(line, (getIntW() / 2) - (lineWidth / 2), nowY);
			nowY += lineHeight + lineInterval;
		}
		backgroud.getGraphics().drawImage(textLayer, 0, 0, null);
		return backgroud;
	}

	@Override
	public int getIntW() { return getContainer().getW(); }

	@Override
	public int getIntH() { return getContainer().getH(); }

}
