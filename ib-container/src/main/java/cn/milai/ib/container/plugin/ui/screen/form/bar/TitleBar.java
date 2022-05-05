package cn.milai.ib.container.plugin.ui.screen.form.bar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import cn.milai.ib.container.plugin.ui.screen.form.ControlMouseEventDispatcher;
import cn.milai.ib.container.plugin.ui.screen.form.MouseDispatcher;
import cn.milai.ib.control.Control;
import cn.milai.ib.control.OpacityControl;
import cn.milai.ib.control.button.CloseButton;
import cn.milai.ib.control.button.MinimizeButton;
import cn.milai.ib.graphics.TextConfig;
import cn.milai.ib.graphics.Texts;

/**
 * 标题栏
 * @author milai
 * @date 2022.04.17
 */
public class TitleBar extends AbstractBar {

	/**
	 * 每个标题栏按钮宽度
	 */
	private static final int TITLE_W = 35;

	/**
	 * 每个标题栏按钮高度
	 */
	private static final int TITLE_H = 20;

	private static final Font DEF_TITLE_FONT = new Font("华文行楷", Font.BOLD, 18);

	/**
	 * 标题栏边距
	 */
	private static final int TITLE_PADDING = 10;

	private static final int TITLE_OPACITY_IN = 255;

	private static final int TITLE_OPACITY_OUT = 100;

	private int titleW = TITLE_W;
	private int titleH = TITLE_H;

	/**
	 * 标题栏按钮
	 */
	private OpacityControl[] titleButtons;

	private static final TextConfig CONFIG = new TextConfig()
		.setColor(Color.WHITE)
		.setBgColor(Color.BLACK)
		.setFont(DEF_TITLE_FONT);

	public TitleBar() {
		titleButtons = new OpacityControl[] {
			new MinimizeButton(TITLE_OPACITY_OUT),
			new CloseButton(TITLE_OPACITY_OUT),
		};
		for (OpacityControl b : titleButtons) {
			b.init(null);
			b.resize(titleW, titleH);
			b.onEnter(e -> b.setOpacity(TITLE_OPACITY_IN)).onExit(e -> b.setOpacity(TITLE_OPACITY_OUT));
			b.setOverBackColor(Color.GRAY);
		}
	}

	@Override
	public void paintTo(BufferedImage base) {
		JFrame form = getForm();
		if (form == null) {
			return;
		}
		Graphics g = base.getGraphics();
		Texts.strokeText(g, form.getTitle(), TITLE_PADDING, TITLE_PADDING * 2, CONFIG);
		double lastX = base.getWidth() - 1 - titleButtons.length * titleW;
		for (Control b : titleButtons) {
			b.setX(lastX);
			lastX += titleW;
			b.getPainter().paintWith(g);
		}
	}

	public MinimizeButton minimizeButton() {
		return (MinimizeButton) titleButtons[0];
	}

	public CloseButton closeButton() {
		return (CloseButton) titleButtons[1];
	}

	/**
	 * 最小宽度，即标题栏按钮的总宽度
	 * @return
	 */
	public int minW() {
		return titleW * titleButtons.length;
	}

	/**
	 * 指定点是否出于可移动区域
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isMovable(double x, double y) {
		for (Control c : titleButtons) {
			if (c.contains(x, y)) {
				return false;
			}
		}
		return y >= 0 && y < titleH;
	}

	@Override
	public MouseDispatcher mouseDispatcher() {
		return new ControlMouseEventDispatcher(titleButtons);
	}

	@Override
	public double getX() { return 0; }

	@Override
	public double getY() { return 0; }

	@Override
	public double getW() { return getForm().getWidth(); }

	@Override
	public double getH() { return titleH; }

}
