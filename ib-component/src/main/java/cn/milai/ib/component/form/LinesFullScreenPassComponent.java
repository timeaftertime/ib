package cn.milai.ib.component.form;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;

import cn.milai.ib.component.form.text.AbstractTextFormComponent;
import cn.milai.ib.component.form.util.ImageTextUtil;
import cn.milai.ib.util.ImageUtil;

/**
 * 全屏显示多行文字渐入、渐出的组件
 * @author milai
 * @date 2020.02.21
 */
public class LinesFullScreenPassComponent extends AbstractTextFormComponent {

	private Image img;
	private long frameCount;
	private long appearEndFrame;
	private long disapearStartFrame;
	private long durationFrames;
	private double delta;
	private double transparency;
	private List<String> lines;
	private int lineInterval;

	/**
	 * 创建一个全屏、背景为黑色、文字行距为 lineInterval 、文件为 lines 、渐入渐出的组件
	 * @param durationFrames
	 * @param lines
	 * @param lineInterval
	 * @param container
	 */
	public LinesFullScreenPassComponent(long durationFrames, List<String> lines, int lineInterval,
		FormContainer container) {
		super(0, 0, container);
		this.durationFrames = durationFrames;
		frameCount = 0;
		appearEndFrame = durationFrames / 3;
		disapearStartFrame = durationFrames / 3 * 2;
		delta = 1.0 / (durationFrames / 3);
		transparency = 0;
		this.lines = lines;
		this.lineInterval = lineInterval;
	}

	@Override
	public Image getImage() {
		frameCount++;
		if (frameCount >= durationFrames) {
			getContainer().removeObject(this);
		}
		if (frameCount >= appearEndFrame && frameCount < disapearStartFrame) {
			return img;
		}
		if (frameCount < appearEndFrame) {
			increaseTransparency();
		} else {
			decreaseTransparency();
		}
		return img = createImage();
	}

	private void decreaseTransparency() {
		transparency -= delta;
		if (transparency < 0) {
			transparency = 0;
		}
	}

	private void increaseTransparency() {
		transparency += delta;
		if (transparency > 1) {
			transparency = 1;
		}
	}

	private Image createImage() {
		BufferedImage backgroud = ImageUtil.newImage(Color.BLACK, getWidth(), getHeight());
		BufferedImage textLayer = ImageUtil.newImage(getWidth(), getHeight());
		Graphics g = ImageUtil.createGraphics(textLayer, (float) transparency);
		g.setFont(getTextFont());
		g.setColor(getTextColor());
		int lineHeight = ImageTextUtil.getTextHeight(g);
		int totalHeight = lines.size() * lineHeight;
		int nowY = (getContainer().getContentHeight() / 2) - (totalHeight / 2) - lineHeight;
		for (String line : lines) {
			int lineWidth = ImageTextUtil.getTextWidth(line, g);
			g.drawString(line, (getWidth() / 2) - (lineWidth / 2), nowY);
			nowY += lineHeight + lineInterval;
		}
		backgroud.getGraphics().drawImage(textLayer, 0, 0, null);
		return backgroud;
	}

	@Override
	public int getWidth() {
		return getContainer().getWidth();
	}

	@Override
	public int getHeight() {
		return getContainer().getHeight();
	}
}
