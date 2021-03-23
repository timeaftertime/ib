package cn.milai.ib.container.plugin.ui.form;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import cn.milai.ib.container.plugin.control.ControlPlugin;
import cn.milai.ib.container.plugin.control.cmd.ClickCmd;
import cn.milai.ib.container.plugin.control.cmd.OverCmd;

/**
 * 鼠标点击事件分发器
 * @author milai
 * @date 2020.03.25
 */
public class MouseEventDispatcher extends MouseAdapter {

	private static final int DEF_FROM_ID = 0;

	private FormUIPlugin ui;

	/**
	 * 创建一个 {@link MouseEventDispatcher} 并将其添加到 {@link FormUIPlugin} 的监听者列表
	 * @param ui
	 */
	MouseEventDispatcher(FormUIPlugin ui) {
		this.ui = ui;
		JFrame form = ui.getForm();
		form.addMouseListener(this);
		form.addMouseMotionListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		ui.getContainer().fire(ControlPlugin.class, controller -> {
			double x = ui.getCamera().toRealX(ui, e.getX());
			double y = ui.getCamera().toRealY(ui, e.getY());
			controller.addCommand(new ClickCmd(DEF_FROM_ID, x, y));
		});
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		ui.getContainer().fire(ControlPlugin.class, controller -> {
			double x = ui.getCamera().toRealX(ui, e.getX());
			double y = ui.getCamera().toRealY(ui, e.getY());
			controller.addCommand(new OverCmd(DEF_FROM_ID, x, y));
		});
	}
}