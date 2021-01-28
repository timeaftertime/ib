package cn.milai.ib.container.ui.form;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import cn.milai.ib.container.control.Command;
import cn.milai.ib.container.control.CommandType;

/**
 * {@link AbstractFormContainer} 的键盘事件分发器
 * @author milai
 * @date 2020.03.25
 */
public class KeyEventDispatcher extends KeyAdapter {

	private static final int DEF_FROM_ID = 0;

	private FormContainer container;

	KeyEventDispatcher(FormContainer container) {
		this.container = container;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		CommandType type = KeyboardMap.findSet(e.getKeyCode());
		if (type == null) {
			return;
		}
		container.addCommand(new Command(type, DEF_FROM_ID));
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// 临时暂停方案
		if (KeyboardMap.isPause(e.getKeyCode())) {
			container.switchPause();
			return;
		}
		CommandType type = KeyboardMap.findUnset(e.getKeyCode());
		if (type == null) {
			return;
		}
		container.addCommand(new Command(type, DEF_FROM_ID));
	}
}