package cn.milai.ib.container.plugin.ui.form;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import cn.milai.ib.container.plugin.control.ControlPlugin;
import cn.milai.ib.container.plugin.control.cmd.BaseCmd;
import cn.milai.ib.container.plugin.control.cmd.CmdType;

/**
 * 键盘事件分发器
 * @author milai
 * @date 2020.03.25
 */
public class KeyEventDispatcher extends KeyAdapter {

	private static final int DEF_FROM_ID = 0;

	private FormUIPlugin ui;

	KeyEventDispatcher(FormUIPlugin ui) {
		this.ui = ui;
		ui.getForm().addKeyListener(this);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (ui.getContainer().isPaused()) {
			return;
		}
		CmdType type = KeyboardMap.findSet(e.getKeyCode());
		if (type == null) {
			return;
		}
		ui.getContainer().fire(ControlPlugin.class, controller -> {
			controller.addCommand(new BaseCmd(type, DEF_FROM_ID));
		});
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 临时暂停方案
		if (KeyboardMap.isPause(e.getKeyCode())) {
			ui.getContainer().switchPause();
			ui.getContainer().fire(ControlPlugin.class, controller -> controller.clearCommands());
			return;
		}
		if (ui.getContainer().isPaused()) {
			return;
		}
		CmdType type = KeyboardMap.findUnset(e.getKeyCode());
		if (type == null) {
			return;
		}
		ui.getContainer().fire(ControlPlugin.class, controller -> {
			controller.addCommand(new BaseCmd(type, DEF_FROM_ID));
		});
	}
}