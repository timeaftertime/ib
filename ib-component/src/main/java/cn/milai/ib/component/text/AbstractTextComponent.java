package cn.milai.ib.component.text;

import java.awt.Color;
import java.awt.Font;

import cn.milai.ib.component.AbstractComponent;
import cn.milai.ib.container.Container;

/**
 * TextFormComtainer 抽象实现
 * @author milai
 * @date 2020.02.21
 */
public abstract class AbstractTextComponent extends AbstractComponent implements TextComponent {

	private Font font;
	private Color color;

	public AbstractTextComponent(int x, int y, Container container) {
		super(x, y, container);
		int textSize = intProp(P_TEXT_SIZE);
		font = new Font(prop(P_TEXT_FONT), Font.BOLD, textSize);
		color = parseColor(intProp(P_TEXT_COLOR));
	}

	protected static Color parseColor(int color) {
		return new Color((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF);
	}

	@Override
	public Font getTextFont() {
		return font;
	}

	@Override
	public Color getTextColor() {
		return color;
	}

}
