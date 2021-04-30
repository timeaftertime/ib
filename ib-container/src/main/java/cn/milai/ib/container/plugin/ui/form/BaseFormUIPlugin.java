package cn.milai.ib.container.plugin.ui.form;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import cn.milai.ib.container.listener.Listeners;
import cn.milai.ib.container.plugin.ui.AbstractUIPlugin;
import cn.milai.ib.container.pluginable.PluginableContainer;
import cn.milai.ib.control.button.Button;
import cn.milai.ib.control.button.CloseButton;
import cn.milai.ib.control.button.MinimizeButton;
import cn.milai.ib.graphics.TextConfig;
import cn.milai.ib.graphics.Texts;

/**
 * {@link FormUIPlugin} 抽象基类
 * @author milai
 */
public class BaseFormUIPlugin extends AbstractUIPlugin implements FormUIPlugin {

	private static final String DEF_TITLE = "敌星弹雨";

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

	private static final TextConfig CONFIG = new TextConfig()
		.setColor(Color.WHITE)
		.setBgColor(Color.BLACK)
		.setFont(DEF_TITLE_FONT);

	private JFrame form;

	/**
	 * 标题栏按钮
	 */
	private Button[] titleButtons;

	@Override
	public JFrame getForm() { return form; }

	@Override
	public int getUIW() { return form.getWidth(); }

	@Override
	public int getUIH() { return form.getHeight(); }

	@Override
	public int getUICH() { return form.getContentPane().getHeight(); }

	@Override
	public void resizeUI(int width, int height) {
		form.setSize(width, height);
		form.setLocationRelativeTo(null);
	}

	@Override
	public String getTitle() { return form.getTitle(); }

	@Override
	public void setTitle(String title) {
		form.setTitle(title);
	}

	@Override
	protected void afterRefresh() {
		if (getContainer().isClosed() || !isRunning()) {
			return;
		}
		form.setVisible(true);
		form.repaint();
	}

	@SuppressWarnings("serial")
	@Override
	protected void initUI() {
		form = new UndecoratedForm() {
			@Override
			public final void paint(Graphics g) {
				PluginableContainer c = getContainer();
				double w = TITLE_W * c.getW() / getUIW();
				double h = TITLE_H * c.getH() / getUIH();
				double lastX = c.getW() - 1 - w * titleButtons.length;
				for (Button b : titleButtons) {
					b.setW(w);
					b.setH(h);
					b.setX(lastX);
					b.setY(0);
					lastX += w;
				}
				BufferedImage base = getNowImage();
				Texts.strokeText(base.getGraphics(), getTitle(), TITLE_PADDING, TITLE_PADDING * 2, CONFIG);
				g.drawImage(base, 0, 0, getWidth(), getHeight(), null);
			}

			@Override
			protected boolean inMoveable(int x, int y) {
				for (Button b : titleButtons) {
					if (b.containsPoint(toRealX(x), toRealY(y))) {
						return false;
					}
				}
				return y >= 0 && y <= TITLE_H;
			}

			@Override
			protected void doRebounds(int preX, int preY, int preW, int preH, int x, int y, int w, int h) {
				int minW = titleButtons.length * TITLE_W;
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
		initTitle();
		new MouseEventDispatcher(this);
		new KeyEventDispatcher(this);
	}

	private void initTitle() {
		setTitle(DEF_TITLE);
		PluginableContainer container = getContainer();
		titleButtons = new Button[] {
			new MinimizeButton(0, 0, container, () -> form.setExtendedState(JFrame.ICONIFIED)),
			new CloseButton(0, 0, container, container::close),
		};
		for (Button b : titleButtons) {
			container.addObject(b);
		}
		container.addObjectListener(Listeners.removedListener((c, b) -> c.addObject(b), titleButtons));
	}

	@Override
	protected void destroyUI() {
		form.setVisible(false);
		form.dispose();
	}

}
