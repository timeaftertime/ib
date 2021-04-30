package cn.milai.ib.control.text;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import cn.milai.ib.IBObject;
import cn.milai.ib.container.Container;
import cn.milai.ib.control.button.Button;
import cn.milai.ib.graphics.Texts;
import cn.milai.ib.graphics.Images;

/**
 * 提供多个选项的组件
 * @author milai
 * @date 2020.04.07
 */
public class Selections extends AbstractTextControl {

	/**
	 * 属性 [选项背景颜色] 的 key
	 */
	public static final String P_SELECTION_BG_COLOR = "selectionBGColor";

	/**
	 * 属性 [选项文字的内边距] 的 key
	 */
	public static final String P_SELECTION_TEXT_PADDING = "selectionTextPadding";

	/**
	 * 属性 [选项文件的外边距] 的 key
	 */
	public static final String P_SELECTION_TEXT_MARGIN = "selectionTextMargin";

	private int value;
	private String[] selections;
	private SelectionButton[] buttons;
	private BufferedImage questionImg;

	public Selections(int x, int y, Container container, String question, String... selections) {
		super(x, y, container);
		this.selections = selections;
		questionImg = Images.newImage(getBGColor(), getIntW(), getIntH());
		Graphics g = questionImg.createGraphics();
		g.setFont(getTextFont());
		Texts.resizeToFit(question, g, getIntW(), getIntH());
		g.drawRect(0, 0, getIntW() - 1, getIntH() - 1);
		g.drawString(
			question,
			(getIntW() - Texts.getTextWidth(question, g)) / 2,
			(getIntH() + Texts.getTextHeight(g)) / 2
		);
		buttons = new SelectionButton[selections.length];
		for (int i = 0; i < buttons.length; i++) {
			int index = i;
			buttons[i] = new SelectionButton(index, () -> {
				onClick(index);
			});
			getContainer().addObject(buttons[i]);
		}
	}

	/**
	 * 获取默认背景颜色
	 * @return
	 */
	private Color getBGColor() { return parseColor(intConf(P_SELECTION_BG_COLOR)); }

	/**
	 * 获取默认内边距
	 * @return
	 */
	private int getPadding() { return intConf(P_SELECTION_TEXT_PADDING); }

	/**
	 * 获取默认外边距
	 * @return
	 */
	private int getMargin() { return intConf(P_SELECTION_TEXT_MARGIN); }

	/**
	 * 将当前组件从容器中移除
	 */
	private void onClick(int index) {
		this.value = index;
		getContainer().removeObject(this);
		for (SelectionButton button : buttons) {
			getContainer().removeObject(button);
		}
	}

	@Override
	public BufferedImage getNowImage() { return questionImg; }

	public int getValue() { return value; }

	private class SelectionButton extends Button {

		private BufferedImage img;

		public SelectionButton(int index, Runnable afterpressed) {
			super(0, 0, Selections.this.getContainer(), afterpressed);
			int width = Selections.this.getIntW();
			int height = Texts.getTextHeight(getTextFont()) + 2 * getPadding();
			img = Images.newImage(getBGColor(), width, height);
			Graphics g = img.createGraphics();
			g.drawRect(0, 0, width - 1, height - 1);
			g.setFont(getTextFont());
			String text = selections[index];
			Texts.resizeToFit(text, g, width - 2 * getPadding(), height - 2 * getPadding());
			g.drawString(
				text,
				(width - Texts.getTextWidth(text, g)) / 2,
				(height + Texts.getTextHeight(g)) / 2
			);
			setX(Selections.this.getX());
			double topY = Selections.this.getY() + Selections.this.getIntH();
			int preSelections = index * (2 * getMargin() + getIntH());
			setY(topY + preSelections + getMargin());
		}

		@Override
		public double doubleConf(String key) {
			if (IBObject.P_WIDTH.equals(key) || IBObject.P_HEIGHT.equals(key)) {
				return 0;
			}
			return super.doubleConf(key);
		}

		public BufferedImage getNowImage() { return img; };

		@Override
		public double getW() { return Selections.this.getW(); }

		@Override
		public double getH() { return img.getHeight(); }

	}

}
