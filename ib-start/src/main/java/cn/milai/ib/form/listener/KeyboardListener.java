package cn.milai.ib.form.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import cn.milai.ib.conf.KeyMap;
import cn.milai.ib.form.Command;

public interface KeyboardListener extends KeyListener {

	@Override
	default void keyPressed(KeyEvent e) {
		Command action = KeyMap.commandOf(e.getKeyCode());
		if (action == null) {
			return;
		}
		keyDown(action);
	}

	@Override
	default void keyReleased(KeyEvent e) {
		Command action = KeyMap.commandOf(e.getKeyCode());
		if (action == null) {
			return;
		}
		keyUp(action);
	}

	@Override
	default void keyTyped(KeyEvent e) {
		// 暂不需要
	}

	void keyDown(Command e);

	void keyUp(Command e);
}
