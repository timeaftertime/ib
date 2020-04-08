package cn.milai.ib.container.form;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.google.common.collect.Lists;

import cn.milai.ib.character.Controllable;
import cn.milai.ib.container.listener.Command;

/**
 * AbstactFormContainer 的键盘事件分发器
 * @author milai
 * @date 2020.03.25
 */
public class KeyEventDispatcher extends KeyAdapter {

	private AbstractFormContainer container;

	KeyEventDispatcher(AbstractFormContainer container) {
		this.container = container;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Command command = KeyboardMap.find(e.getKeyCode());
		if (command == null) {
			return;
		}
		for (Controllable controllable : Lists.newArrayList(container.getControllables())) {
			if (!controllable.onReceive(command)) {
				return;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Command command = KeyboardMap.find(e.getKeyCode());
		if (command == null) {
			return;
		}
		if (command == Command.PAUSE) {
			container.switchPause();
			return;
		}
		for (Controllable controllable : Lists.newArrayList(container.getControllables())) {
			if (!controllable.onCancel(command)) {
				return;
			}
		}
	}
}