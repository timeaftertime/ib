package cn.milai.ib.control.text;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import cn.milai.ib.config.ProtoConfiguarable;
import cn.milai.ib.control.button.SelectionButton;
import cn.milai.ib.graphics.Images;
import cn.milai.ib.graphics.Texts;
import cn.milai.ib.obj.BasePainter;
import cn.milai.ib.obj.property.Painter;

/**
 * 提供多个选项的组件
 * @author milai
 * @date 2020.04.07
 */
@ProtoConfiguarable
public class Selections extends AbstractTextControl {

	private int value;
	private List<SelectionButton> buttons = new ArrayList<>();
	private BufferedImage questionImg;
	private Color bgColor = Color.BLACK;
	private String question;
	private int selectionTextPadding = 4;
	private int selectionTextMargin = 7;

	public Selections(String question, String... selections) {
		this.question = question;
		refreshQuestionImage();
		for (int i = 0; i < selections.length; i++) {
			addSelection(selections[i], i);
		}
	}

	private void addSelection(String text, int value) {
		SelectionButton button = new SelectionButton(this, value, text);
		button.setH(Texts.getTextHeight(getTextProperty().getFont()) + 2 * getSelectionTextPadding());
		button.setW(getW());
		button.setX(getX());
		button.setY(getY() + getH() + buttons.size() * 2);
		buttons.add(button);
		container().addObject(button);
	}

	public void setBGColor(Color bgColor) { this.bgColor = bgColor; }

	public Color getBGColor() { return bgColor; }

	public int getSelectionTextPadding() { return selectionTextPadding; }

	public void setSelectionTextPadding(int selectionTextPadding) { this.selectionTextPadding = selectionTextPadding; }

	public int getSelectionTextMargin() { return selectionTextMargin; }

	public void setSelectionTextMargin(int selectionTextMargin) { this.selectionTextMargin = selectionTextMargin; }

	private void refreshQuestionImage() {
		questionImg = Images.newImage(bgColor, getIntW(), getIntH());
		Graphics g = questionImg.createGraphics();
		g.setFont(getTextProperty().getFont());
		Texts.resizeToFit(question, g, getIntW(), getIntH());
		g.drawRect(0, 0, getIntW() - 1, getIntH() - 1);
		g.drawString(
			question,
			(getIntW() - Texts.getTextWidth(question, g)) / 2,
			(getIntH() + Texts.getTextHeight(g)) / 2
		);
	}

	@Override
	protected Painter initPainter() {
		return new BasePainter() {
			@Override
			public BufferedImage getNowImage() { return questionImg; }
		};
	}

	public int getValue() { return value; }

	public void select(int value) {
		this.value = value;
		container().removeObject(this);
		for (SelectionButton button : buttons) {
			container().removeObject(button);
		}
	}

}
