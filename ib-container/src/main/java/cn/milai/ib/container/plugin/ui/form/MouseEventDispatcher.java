package cn.milai.ib.container.plugin.ui.form;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import cn.milai.ib.container.plugin.control.ControlPlugin;
import cn.milai.ib.container.plugin.control.cmd.ClickCmd;

/**
 * 鼠标点击事件分发器
 * @author milai
 * @date 2020.03.25
 */
public class MouseEventDispatcher extends MouseAdapter {

	private static final int DEF_FROM_ID = 0;

	private FormUIPlugin ui;

	MouseEventDispatcher(FormUIPlugin ui) {
		this.ui = ui;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		ui.getContainer().fire(ControlPlugin.class, controller -> {
			double x = ui.getCamera().toRealX(ui, e.getX());
			double y = ui.getCamera().toRealY(ui, e.getY());
			controller.addCommand(new ClickCmd(DEF_FROM_ID, x, y));
		});

	}
}