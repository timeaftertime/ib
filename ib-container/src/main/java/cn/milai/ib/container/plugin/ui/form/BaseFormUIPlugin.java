package cn.milai.ib.container.plugin.ui.form;

import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import cn.milai.ib.container.plugin.ui.AbstractUIPlugin;

/**
 * {@link FormUIPlugin} 抽象基类
 * @author milai
 */
public class BaseFormUIPlugin extends AbstractUIPlugin implements FormUIPlugin {

	/**
	 * 默认标题
	 */
	private static final String DEF_TITLE = "敌星弹雨";

	private JFrame form;

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
		form = new JFrame() {
			@Override
			public void paint(Graphics g) {
				g.drawImage(getNowImage(), 0, 0, getWidth(), getHeight(), null);
			}
		};
		form.setLocationRelativeTo(null);
		form.setLayout(null);
		form.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		form.addMouseListener(new MouseEventDispatcher(this));
		form.addKeyListener(new KeyEventDispatcher(this));
		form.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				getContainer().close();
			}
		});
		setTitle(DEF_TITLE);
	}

	@Override
	protected void destroyUI() {
		form.setVisible(false);
		form.dispose();
	}

	@Override
	protected void onReset() {}

}
