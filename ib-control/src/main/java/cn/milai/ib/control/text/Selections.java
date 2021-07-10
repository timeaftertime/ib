package cn.milai.ib.control.text;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import cn.milai.ib.control.button.SelectionButton;
import cn.milai.ib.graphics.Images;
import cn.milai.ib.graphics.Texts;
import cn.milai.ib.item.BasePainter;
import cn.milai.ib.item.property.Painter;

/**
 * 提供多个选项的组件
 * @author milai
 * @date 2020.04.07
 */
public class Selections extends AbstractTextControl {

	private static final int INTERVAL_H = 10;

	private int value;
	private List<SelectionButton> buttons = new ArrayList<>();
	private BufferedImage questionImg;
	private Color bgColor = Color.BLACK;
	private String question;
	private String[] selections;
	private int selectionTextPadding = 4;
	private int selectionTextMargin = 7;

	public Selections(String question, String... selections) {
		this.question = question;
		this.selections = selections;
	}

	@Override
	protected void initItem() {
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
		double topY = getY() + getH() + INTERVAL_H;
		button.setY(topY + (INTERVAL_H + button.getH()) * value);
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
	protected Painter createPainter() {
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
