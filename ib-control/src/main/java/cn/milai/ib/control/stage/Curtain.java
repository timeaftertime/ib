package cn.milai.ib.control.stage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.listener.LifecycleListener;
import cn.milai.ib.control.PassCaculator;
import cn.milai.ib.control.StageControl;
import cn.milai.ib.control.text.AbstractTextControl;
import cn.milai.ib.graphics.Images;
import cn.milai.ib.graphics.Texts;
import cn.milai.ib.item.BasePainter;
import cn.milai.ib.item.Layers;
import cn.milai.ib.item.property.Painter;

/**
 * 舞台幕布
 * @author milai
 * @date 2020.02.21
 */
public class Curtain extends AbstractTextControl implements LifecycleListener, StageControl {

	private PassCaculator pass;
	private BufferedImage img;
	private List<String> lines;
	private int lineInterval;
	private long inFrame;
	private long keepFrame;
	private long outFrame;

	/**
	 * 创建一个全屏、背景为黑色、文字行距为 lineInterval 、文字为 lines 、渐入渐出的组件
	 * @param inFrame 渐入持续的帧数
	 * @param keepFrame 不变持续的帧数
	 * @param outFrame 渐出持续的帧数
	 * @param lines
	 * @param lineInterval
	 */
	public Curtain(long inFrame, long keepFrame, long outFrame, List<String> lines, int lineInterval) {
		this.inFrame = inFrame;
		this.keepFrame = keepFrame;
		this.outFrame = outFrame;
		this.lines = lines;
		this.lineInterval = lineInterval;
	}

	@Override
	public void initItem() {
		LifecycleContainer c = (LifecycleContainer) container();
		pass = new PassCaculator(inFrame, keepFrame, outFrame, p -> {
			c.removeLifecycleListener(this);
			c.removeObject(this);
		});
		c.addLifecycleListener(this);
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
				if (!pass.isKeep()) {
					img = createImage();
				}
				return img;
			}
		};
	}

	private BufferedImage createImage() {
		BufferedImage backgroud = Images.newImage(Color.BLACK, getIntW(), getIntH());
		BufferedImage textLayer = Images.newImage(getIntW(), getIntH());
		Graphics g = Images.createGraphics(
			textLayer, 1.0f * pass.getTransparency() / PassCaculator.MAX_TRANSPARENCY
		);
		g.setFont(getFont());
		g.setColor(getColor());
		int lineHeight = Texts.getTextHeight(g);
		int totalHeight = lines.size() * lineHeight;
		int nowY = (stage().getIntH() / 2) - (totalHeight / 2);
		for (String line : lines) {
			int lineWidth = Texts.getTextWidth(line, g);
			g.drawString(line, (getIntW() / 2) - (lineWidth / 2), nowY);
			nowY += lineHeight + lineInterval;
		}
		backgroud.getGraphics().drawImage(textLayer, 0, 0, null);
		return backgroud;
	}

	@Override
	public double getW() { return stage().getW(); }

	@Override
	public double getH() { return stage().getH(); }

	@Override
	public int getZ() { return Layers.CURTAIN.getZ(); }

}
