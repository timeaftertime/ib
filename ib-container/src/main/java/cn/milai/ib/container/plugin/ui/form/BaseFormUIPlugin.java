package cn.milai.ib.container.plugin.ui.form;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import cn.milai.ib.container.listener.ContainerListeners;
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

	private KeyEventDispatcher keyEventDispatcher;

	private MouseEventDispatcher mouseEventDispatcher;

	public void setKeyMapping(KeyMapping keyMapping) {
		KeyEventDispatcher newDispatcher = new KeyEventDispatcher(this, keyMapping);
		if (isRunning()) {
			getForm().removeKeyListener(this.keyEventDispatcher);
			setKeyDispatcher(newDispatcher);
		}
		this.keyEventDispatcher = newDispatcher;
	}

	private void setKeyDispatcher(KeyEventDispatcher newDispatcher) {
		getForm().addKeyListener(newDispatcher);
	}

	public void setMouseMapping(MouseMapping mouseMapping) {
		MouseEventDispatcher newDispatcher = new MouseEventDispatcher(this, mouseMapping);
		if (isRunning()) {
			getForm().removeMouseListener(this.mouseEventDispatcher);
			getForm().removeMouseMotionListener(this.mouseEventDispatcher);
			setMouseEventDispatcher(newDispatcher);
		}
		this.mouseEventDispatcher = newDispatcher;
	}

	private void setMouseEventDispatcher(MouseEventDispatcher newDispatcher) {
		getForm().addMouseListener(newDispatcher);
		getForm().addMouseMotionListener(newDispatcher);
	}

	/**
	 * 标题栏按钮
	 */
	private Button[] titleButtons;

	@Override
	public JFrame getForm() { return form; }

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

	private void initTitle() {
		setTitle(DEF_TITLE);
		PluginableContainer container = getContainer();
		titleButtons = new Button[] {
			new MinimizeButton(() -> form.setExtendedState(JFrame.ICONIFIED)),
			new CloseButton(container::close),
		};
		for (Button b : titleButtons) {
			container.addObject(b);
		}
		container.addObjectListener(ContainerListeners.removedListener((c, b) -> c.addObject(b), titleButtons));
	}

	@Override
	protected void stopUIPlugin() {
		form.setVisible(false);
		form.dispose();
	}

	@Override
	public void refreshUIPlugin() {
		if (getContainer().isClosed() || !isRunning()) {
			return;
		}
		form.repaint();
	}

	@Override
	protected void doReset() {
		initTitle();
	}

	@SuppressWarnings("serial")
	@Override
	protected void startUIPlugin() {
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
				BufferedImage base = getUI();
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
		form.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				getContainer().close();
			}
		});
		setKeyDispatcher(keyEventDispatcher);
		setMouseEventDispatcher(mouseEventDispatcher);
		initTitle();

		form.repaint();
		form.setVisible(true);
	}

}
