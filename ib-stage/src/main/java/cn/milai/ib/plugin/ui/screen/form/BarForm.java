package cn.milai.ib.plugin.ui.screen.form;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.swing.JFrame;

import cn.milai.ib.graphics.Images;
import cn.milai.ib.plugin.ui.screen.form.bar.Bar;
import cn.milai.ib.plugin.ui.screen.form.bar.TitleBar;

/**
 * 可组装 {@link Bar} 的 {@link UndecoratedForm}
 * @author milai
 * @date 2022.04.17
 */
public abstract class BarForm extends UndecoratedForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Set<Bar> bars = new HashSet<>();
	private TitleBar titleBar = new TitleBar();

	public BarForm() {
		BarMouseDispatcher mouseDispatcher = new BarMouseDispatcher();
		addMouseListener(mouseDispatcher);
		addMouseMotionListener(mouseDispatcher);
		BarKeyDispatcher keyDispatcher = new BarKeyDispatcher();
		addKeyListener(keyDispatcher);
	}

	/**
	 * 设置当前 {@link TitleBar}
	 * @param titleBar
	 */
	public void setTitleBar(TitleBar titleBar) {
		bars.remove(this.titleBar);
		if (titleBar != null) {
			this.titleBar = titleBar;
			bars.add(this.titleBar);
			titleBar.setForm(this);
			setSize(Math.max(getWidth(), minW()), Math.max(getHeight(), minH()));
		}
	}

	@Override
	public final void paint(Graphics g) {
		BufferedImage base = Images.newImage(getWidth(), getHeight());
		Graphics2D g2d = base.createGraphics();
		g2d.drawImage(baseImage(), 0, 0, getWidth(), getHeight(), null);
		for (Bar bar : bars) {
			bar.paintTo(base);
		}
		g.drawImage(base, 0, 0, null);
	}

	private class BarKeyDispatcher extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			dispatch(d -> d::keyPressed, e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			dispatch(d -> d::keyReleased, e);
		}

		@Override
		public void keyTyped(KeyEvent e) {
			dispatch(d -> d::keyTyped, e);
		}

		private void dispatch(Function<KeyDispatcher, Consumer<KeyEvent>> fun, KeyEvent e) {
			for (Bar bar : bars) {
				KeyDispatcher dispatcher = bar.KeyDispatcher();
				if (dispatcher != null) {
					fun.apply(dispatcher).accept(e);
					if (e.isConsumed()) {
						break;
					}
				}
			}
		}

	}

	private class BarMouseDispatcher extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			dispatch(d -> d::mouseClicked, e);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			dispatch(d -> d::mouseMoved, e);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			dispatch(d -> d::mousePressed, e);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			dispatch(d -> d::mouseReleased, e);
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			dispatch(d -> d::mouseDragged, e);
		}

		private void dispatch(Function<MouseDispatcher, Consumer<MouseEvent>> fun, MouseEvent e) {
			for (Bar bar : bars) {
				MouseDispatcher dispatcher = bar.mouseDispatcher();
				if (dispatcher != null) {
					fun.apply(dispatcher).accept(e);
					if (e.isConsumed()) {
						break;
					}
				}
			}
		}
	}

	@Override
	protected boolean inMoveable(int x, int y) {
		return titleBar == null ? false : titleBar.isMovable(x, y);
	}

	@Override
	protected void doRebounds(int preX, int preY, int preW, int preH, int x, int y, int w, int h) {
		int minW = minW();
		int minH = minH();
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
		simpleRebounds(x, y, w, h);
	}

	private int minW() {
		return titleBar == null ? 0 : titleBar.minW();
	}

	private int minH() {
		return (int) (titleBar == null ? 0 : titleBar.getH());
	}

	/**
	 * 添加一个 {@link MouseDispatcher}
	 * @param dispatcher
	 */
	public void addMouseDispatcher(MouseDispatcher dispatcher) {
		addMouseListener(dispatcher);
		addMouseMotionListener(dispatcher);
	}

	/**
	 * 添加一个 {@link KeyDispatcher}
	 * @param dispatcher
	 */
	public void addKeyDispatcher(KeyDispatcher dispatcher) {
		addKeyListener(dispatcher);
	}

	/**
	 * 获取当前 {@link JFrame} 基础 {@link BufferedImage}
	 * @return
	 */
	protected abstract BufferedImage baseImage();

}
