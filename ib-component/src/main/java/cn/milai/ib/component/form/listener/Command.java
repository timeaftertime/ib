package cn.milai.ib.component.form.listener;

import java.awt.event.KeyEvent;

public enum Command {

	UP(KeyEvent.VK_W), DOWN(KeyEvent.VK_S), LEFT(KeyEvent.VK_A), RIGHT(KeyEvent.VK_D), SHOOT(KeyEvent.VK_J), PAUSE(KeyEvent.VK_P);

	/**
	 * 对应的键盘按键值
	 * @see KeyEvent
	 */
	private int keyCode;

	Command(int keyCode) {
		this.keyCode = keyCode;
	}

	public static Command findByKeyCode(int keyCode) {
		for (Command command : Command.values()) {
			if (command.keyCode == keyCode) {
				return command;
			}
		}
		return null;
	}
}
