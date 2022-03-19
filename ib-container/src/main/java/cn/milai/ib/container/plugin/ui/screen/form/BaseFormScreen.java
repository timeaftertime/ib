package cn.milai.ib.container.plugin.ui.screen.form;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.plugin.ui.UIPlugin;
import cn.milai.ib.container.plugin.ui.screen.BaseScreen;
import cn.milai.ib.control.Control;
import cn.milai.ib.control.OpacityControl;
import cn.milai.ib.control.button.CloseButton;
import cn.milai.ib.control.button.MinimizeButton;
import cn.milai.ib.graphics.Images;
import cn.milai.ib.graphics.TextConfig;
import cn.milai.ib.graphics.Texts;

/**
 * {@link FormScreen} 实现
 * @author milai
 * @date 2022.03.16
 */
public class BaseFormScreen extends BaseScreen implements FormScreen {

	/**
	 * 每个标题栏按钮宽度
	 */
	private static final int TITLE_W = 30;

	/**
	 * 每个标题栏按钮高度
	 */
	private static final int TITLE_H = 18;

	private static final Font DEF_TITLE_FONT = new Font("华文行楷", Font.BOLD, TITLE_H);

	/**
	 * 标题栏边距
	 */
	private static final int TITLE_PADDING = 10;

	private static final int TITLE_OPACITY_IN = 255;

	private static final int TITLE_OPACITY_OUT = 100;

	private int titleW = TITLE_W;
	private int titleH = TITLE_H;

	private static final TextConfig CONFIG = new TextConfig()
		.setColor(Color.WHITE)
		.setBgColor(Color.BLACK)
		.setFont(DEF_TITLE_FONT);

	private JFrame form;

	private KeyMapping keyMapping;
	private MouseMapping mouseMapping;

	private MouseEventDispatcher mouseDispatcher;
	private KeyEventDispatcher keyDispatcher;

	@Override
	public void setKeyMapping(KeyMapping keyMapping) { this.keyMapping = keyMapping; }

	@Override
	public KeyMapping getKeyMapping() { return keyMapping; }

	@Override
	public void setMouseMapping(MouseMapping mouseMapping) { this.mouseMapping = mouseMapping; }

	@Override
	public MouseMapping getMouseMapping() { return mouseMapping; }

	/**
	 * 标题栏按钮
	 */
	private OpacityControl[] titleButtons;

	@Override
	public JFrame form() {
		return form;
	}

	@Override
	public void onDestroyed() {
		form.setVisible(false);
		form.dispose();
	}

	@Override
	public void onRefreshed() {
		form.repaint();
	}

	@SuppressWarnings("serial")
	@Override
	public void onInit() {
		form = new UndecoratedForm() {
			@Override
			public final void paint(Graphics g) {
				UIPlugin ui = ui();
				if (ui == null) {
					return;
				}
				BufferedImage base = ui().getUI();
				if (base.getWidth() < titleW * titleButtons.length || base.getHeight() < titleH) {
					BufferedImage newImage = Images.newImage(titleW, titleH);
					newImage.createGraphics().drawImage(
						base, 0, 0, newImage.getWidth(), newImage.getHeight(), null
					);
					base = newImage;
				}
				// 标题
				Texts.strokeText(base.getGraphics(), ui().getTitle(), TITLE_PADDING, TITLE_PADDING * 2, CONFIG);
				// 按钮
				for (Control b : titleButtons) {
					b.getPainter().paintWith(base.getGraphics());
				}

				g.drawImage(base, 0, 0, getWidth(), getHeight(), null);
			}

			@Override
			protected boolean inMoveable(int x, int y) {
				for (Control b : titleButtons) {
					if (b.contains(x, y)) {
						return false;
					}
				}
				return y >= 0 && y <= titleH;
			}

			@Override
			protected void doRebounds(int preX, int preY, int preW, int preH, int x, int y, int w, int h) {
				int minW = minWidth();
				int minH = TITLE_H;
				if (w < minW) {
					if (x != preX) {
						int dir = x > preX ? 1 : -1;
						x = preX + dir * (preW - minW);
					}
					w = minW;
				}
				if (h < minH) {
					if (y != preY) {
						int dir = y > preY ? 1 : -1;
						y = preY + dir * (preH - minH);
					}
					h = minH;
				}
				setBounds(x, y, w, h);
			}

		};

		form.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				ui().containers().forEach(LifecycleContainer::close);
				form.removeWindowListener(this);
			}
		});

		MinimizeButton minButton = new MinimizeButton(
			TITLE_OPACITY_OUT
		);
		minButton.onPressUp(e -> form.setExtendedState(JFrame.ICONIFIED));
		CloseButton closeButton = new CloseButton(TITLE_OPACITY_OUT);
		closeButton.onPressUp(e -> ui().containers().forEach(LifecycleContainer::close));
		titleButtons = new OpacityControl[] { minButton, closeButton };
		for (OpacityControl b : titleButtons) {
			b.init(null);
			b.resize(titleW, titleH);
			b.onEnter(e -> b.setOpacity(TITLE_OPACITY_IN)).onExit(e -> b.setOpacity(TITLE_OPACITY_OUT));
		}

		mouseDispatcher = new MouseEventDispatcher(this);
		form.addMouseListener(mouseDispatcher);
		form.addMouseMotionListener(mouseDispatcher);
		MouseEventDispatcher titleButtonDispatcher = new ControlMouseEventDispatcher(
			titleButtons, this
		);
		form.addMouseListener(titleButtonDispatcher);
		form.addMouseMotionListener(titleButtonDispatcher);
		keyDispatcher = new KeyEventDispatcher(this);
		form.addKeyListener(keyDispatcher);

		form.repaint();
		form.setVisible(true);

	}

	@Override
	public void onRelocationed() {
		form.setLocation(ui().getIntX(), ui().getIntY());
	}

	@Override
	public void onResized() {
		form.setLocation(
			(int) (form.getX() + form.getWidth() / 2 - ui().getW() / 2),
			(int) (form.getY() + form.getHeight() / 2 - ui().getH() / 2)
		);
		form.setSize(Math.max(ui().getIntW(), minWidth()), Math.max(ui().getIntH(), minHeight()));
		double lastX = form.getWidth() - 1 - titleW * titleButtons.length;
		for (Control b : titleButtons) {
			b.setX(lastX);
			lastX += b.getW();
		}
	}

	private int minWidth() {
		return titleButtons.length * titleW;
	}

	private int minHeight() {
		return titleH;
	}

	@Override
	public double getW() { return form == null ? 0 : form.getWidth(); }

	@Override
	public void setW(double w) {
		if (form == null) {
			return;
		}
		form.setSize((int) w, form.getHeight());
	}

	@Override
	public void setH(double h) {
		if (form == null) {
			return;
		}
		form.setSize(form.getWidth(), (int) h);
	}

	@Override
	public double getH() { return form == null ? 0 : form.getHeight(); }

}
