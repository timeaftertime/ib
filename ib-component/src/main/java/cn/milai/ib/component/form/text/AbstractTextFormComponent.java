package cn.milai.ib.component.form.text;

import java.awt.Color;
import java.awt.Font;

import cn.milai.ib.component.form.AbstractFormComponent;
import cn.milai.ib.component.form.FormContainer;

/**
 * TextFormComtainer 抽象实现
 * @author milai
 * @date 2020.02.21
 */
public abstract class AbstractTextFormComponent extends AbstractFormComponent implements TextFormComponent {

	private Font font;
	private Color color;

	public AbstractTextFormComponent(int x, int y, FormContainer container) {
		super(x, y, container);
		int textSize = proratedIntProp(P_TEXT_SIZE);
		font = new Font(prop(P_TEXT_FONT), Font.BOLD, textSize);
		color = parseColor(intProp(P_TEXT_COLOR));
	}

	private static Color parseColor(int color) {
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
