package cn.milai.ib.control.text;

import java.awt.Color;
import java.awt.Font;

import cn.milai.ib.container.Container;
import cn.milai.ib.control.AbstractControl;

/**
 * {@link TextControl} 抽象实现
 * @author milai
 * @date 2020.02.21
 */
public abstract class AbstractTextControl extends AbstractControl implements TextControl {

	private Font font;
	private Color color;

	public AbstractTextControl(int x, int y, Container container) {
		super(x, y, container);
		int textSize = intConf(P_TEXT_SIZE);
		font = new Font(conf(P_TEXT_FONT), Font.BOLD, textSize);
		color = parseColor(intConf(P_TEXT_COLOR));
	}

	protected static Color parseColor(int color) {
		return new Color((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF);
	}

	@Override
	public Font getTextFont() { return font; }

	@Override
	public Color getTextColor() { return color; }

}
