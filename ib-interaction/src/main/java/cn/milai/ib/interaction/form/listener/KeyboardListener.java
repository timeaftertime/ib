package cn.milai.ib.interaction.form.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public interface KeyboardListener extends KeyListener {

	@Override
	default void keyPressed(KeyEvent e) {
		Command action = Command.findByKeyCode(e.getKeyCode());
		if (action == null) {
			return;
		}
		keyDown(action);
	}

	@Override
	default void keyReleased(KeyEvent e) {
		Command action = Command.findByKeyCode(e.getKeyCode());
		if (action == null) {
			return;
		}
		keyUp(action);
	}

	@Override
	default void keyTyped(KeyEvent e) {
		// 暂不需要
	}

	/**
	 * 响应给定按下键盘命令，并返回是否成功完成命令
	 * @param e
	 * @return
	 */
	boolean keyDown(Command e);

	/**
	 * 响应给定放开键盘命令，并返回是否成功完成命令
	 * @param e
	 * @return
	 */
	boolean keyUp(Command e);
}
