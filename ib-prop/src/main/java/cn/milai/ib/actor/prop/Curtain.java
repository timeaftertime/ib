package cn.milai.ib.actor.prop;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import cn.milai.ib.actor.Layers;
import cn.milai.ib.actor.nature.BasePainter;
import cn.milai.ib.actor.nature.Painter;
import cn.milai.ib.actor.prop.text.AbstractTextProp;
import cn.milai.ib.actor.prop.util.InKeepOut;
import cn.milai.ib.actor.prop.util.PassCaculator;
import cn.milai.ib.graphics.Images;
import cn.milai.ib.graphics.Texts;
import cn.milai.ib.stage.Stage;

/**
 * 幕布
 * @author milai
 * @date 2020.02.21
 */
public class Curtain extends AbstractTextProp {

	private InKeepOut inKeepOut;
	private BufferedImage img;
	private List<String> lines;
	private int lineInterval;

	/**
	 * 创建一个全屏、背景为黑色、文字行距为 lineInterval 、文字为 lines 、渐入渐出的组件
	 * @param inFrame 渐入持续的帧数
	 * @param keepFrame 不变持续的帧数
	 * @param outFrame 渐出持续的帧数
	 * @param lines
	 * @param lineInterval
	 */
	public Curtain(long inFrame, long keepFrame, long outFrame, List<String> lines, int lineInterval) {
		inKeepOut = new InKeepOut(this, inFrame, keepFrame, outFrame);
		this.lines = lines;
		this.lineInterval = lineInterval;
	}

	@Override
	protected void onEnterStage(Stage stage) {
		inKeepOut.start(stage);
	}

	@Override
	protected Painter createPainter() {
		return new BasePainter(this) {
			@Override
			public BufferedImage getNowImage() {
				if (!inKeepOut.isKeep()) {
					img = createImage();
				}
				return img;
			}
		};
	}

	private BufferedImage createImage() {
		int w = getIntW();
		int h = getIntH();
		if (w <= 0 || h <= 0) {
			return Images.EMPTY;
		}
		BufferedImage backgroud = Images.newImage(Color.BLACK, w, h);
		BufferedImage textLayer = Images.newImage(w, h);
		Graphics g = Images.createGraphics(
			textLayer, 1.0f * inKeepOut.getTransparency() / PassCaculator.MAX_TRANSPARENCY
		);
		g.setFont(getFont());
		g.setColor(getColor());
		int lineHeight = Texts.getTextHeight(g);
		int totalHeight = lines.size() * lineHeight;
		int nowY = (stage().getIntH() / 2) - (totalHeight / 2);
		for (String line : lines) {
			int lineWidth = Texts.getTextWidth(line, g);
			g.drawString(line, (w / 2) - (lineWidth / 2), nowY);
			nowY += lineHeight + lineInterval;
		}
		backgroud.getGraphics().drawImage(textLayer, 0, 0, null);
		return backgroud;
	}

	@Override
	public double getW() {
		Stage stage = stage();
		return stage == null ? 0 : stage.getW();
	}

	@Override
	public double getH() {
		Stage stage = stage();
		return stage == null ? 0 : stage.getH();
	}

	@Override
	public int getZ() { return Layers.CURTAIN.getZ(); }

}
