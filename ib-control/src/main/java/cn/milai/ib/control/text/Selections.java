package cn.milai.ib.control.text;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import cn.milai.ib.control.button.Selection;
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

	private static final int DEF_INTERVAL_H = 10;

	private int value;
	private List<Selection> buttons = new ArrayList<>();
	private BufferedImage questionImg;
	private String question;
	private String[] selections;
	private int selectionInterval = DEF_INTERVAL_H;

	public Selections(String question, String... selections) {
		this.question = question;
		this.selections = selections;
		setPadding(4);
	}

	@Override
	protected void initItem() {
		refreshQuestionImage();
		for (int i = 0; i < selections.length; i++) {
			addSelection(selections[i], i);
		}
	}

	private Selection addSelection(String text, int value) {
		Selection s = new Selection(this, value, text);
		s.setColor(getColor());
		s.setBgColor(getBgColor());
		s.setFontName(getFontName());
		s.setSize(getSize());
		s.setPadding(getPadding());
		s.setStyle(getStyle());
		s.setX(getX());
		double topY = getY() + getH() + selectionInterval;
		s.setY(topY + (selectionInterval + s.getH()) * value);
		buttons.add(s);
		container().addObject(s);
		return s;
	}

	public int getSelectionInterval() { return selectionInterval; }

	public void setSelectionInterval(int selectionInterval) { this.selectionInterval = selectionInterval; }

	private void refreshQuestionImage() {
		questionImg = Images.newImage(getBgColor(), getIntW(), getIntH());
		Graphics g = questionImg.createGraphics();
		g.setFont(getFont());
		Texts.resizeToFit(question, g, getIntW(), getIntH());
		g.drawRect(0, 0, getIntW() - 1, getIntH() - 1);
		g.drawString(
			question,
			(getIntW() - Texts.getTextWidth(question, g)) / 2,
			(getIntH() + Texts.getTextHeight(g)) / 2
		);
	}

	public int getValue() { return value; }

	public void select(int value) {
		this.value = value;
		container().removeObject(this);
		for (Selection button : buttons) {
			container().removeObject(button);
		}
	}

	public String getQuestion() { return question; }

	public void setQuestion(String question) { this.question = question; }

	@Override
	protected Painter createPainter() {
		return new BasePainter() {
			@Override
			public BufferedImage getNowImage() { return questionImg; }
		};
	}

}
