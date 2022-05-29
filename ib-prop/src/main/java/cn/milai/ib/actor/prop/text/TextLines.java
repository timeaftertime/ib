package cn.milai.ib.actor.prop.text;

import java.awt.Color;
import java.awt.image.BufferedImage;

import cn.milai.ib.actor.prop.util.InKeepOut;
import cn.milai.ib.graphics.Images;
import cn.milai.ib.stage.Stage;

/**
 * 显示多行文字的 {@link Label}
 * @author milai
 * @date 2020.04.05
 */
public class TextLines extends Label {

	private InKeepOut inKeepOut;

	private final Color DEFAULT_BG_COLOR = Color.BLACK;

	/**
	 * 创建一个多行文本
	 * @param inFrame 渐入的持续帧数
	 * @param keepFrame 维持不变的持续帧数
	 * @param outFrame 渐出的持续帧数
	 * @param lines 需要显示的文本
	 */
	public TextLines(long inFrame, long keepFrame, long outFrame, String... lines) {
		this.inKeepOut = new InKeepOut(this, inFrame, keepFrame, outFrame);
		setBgColor(DEFAULT_BG_COLOR);
		setWrap(true);
		setText(String.join(Label.WRAP, lines));
	}

	@Override
	protected void onEnterStage(Stage stage) {
		inKeepOut.start(stage);
	}

	@Override
	protected BufferedImage getLabelImg(BufferedImage img) {
		if (inKeepOut.isKeep()) {
			return img;
		}
		return Images.transparent(Images.copy(img), inKeepOut.getTransparency());
	}

}
