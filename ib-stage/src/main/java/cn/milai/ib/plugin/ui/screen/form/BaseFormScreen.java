package cn.milai.ib.plugin.ui.screen.form;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import cn.milai.common.thread.BlockCondition;
import cn.milai.common.thread.Condition;
import cn.milai.ib.plugin.ui.screen.BaseScreen;
import cn.milai.ib.plugin.ui.screen.form.bar.TitleBar;

/**
 * {@link FormScreen} 实现
 * @author milai
 * @date 2022.03.16
 */
public class BaseFormScreen extends BaseScreen implements FormScreen {

	private BarForm form;
	private TitleBar titleBar;
	private Condition initialized = BlockCondition.newSimple();

	private KeyMapping keyMapping;
	private MouseMapping mouseMapping;

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
		titleBar = new TitleBar();
		titleBar.minimizeButton().onPressUp().subscribe(e -> form.setExtendedState(JFrame.ICONIFIED));
		titleBar.closeButton().onPressUp().subscribe(e -> ui().stage().lifecycle().close());
		form = new BarForm() {
			@Override
			protected BufferedImage baseImage() {
				return ui().getUI();
			}

			@Override
			public String getTitle() { return ui().getTitle(); }

			@Override
			public void setTitle(String title) {
				ui().setTitle(title);
			}

		};
		form.addMouseDispatcher(new FormScreenMouseDispatcher(this));
		form.addKeyDispatcher(new FormScreenKeyDispatcher(this));
		form.setTitleBar(titleBar);

		form.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				form.removeWindowListener(this);
				ui().stage().lifecycle().close();
			}
		});
		form.setVisible(true);

		initialized.toMet();
	}

	@Override
	public void onRelocationed() {
		form.setLocation(ui().getIntX(), ui().getIntY());
	}

	@Override
	public void onResized() {
		double centerX = form.getX() + form.getWidth() / 2.0;
		double centerY = form.getY() + form.getHeight() / 2.0;
		form.setLocation((int) (centerX - ui().getW() / 2), (int) (centerY - ui().getH() / 2));
		form.setSize(ui().getIntW(), ui().getIntH());
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

	@Override
	public void setKeyMapping(KeyMapping keyMapping) { this.keyMapping = keyMapping; }

	@Override
	public KeyMapping getKeyMapping() { return keyMapping; }

	@Override
	public void setMouseMapping(MouseMapping mouseMapping) { this.mouseMapping = mouseMapping; }

	@Override
	public MouseMapping getMouseMapping() { return mouseMapping; }

}
