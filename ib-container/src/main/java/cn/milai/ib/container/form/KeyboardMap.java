package cn.milai.ib.container.form;

import java.awt.event.KeyEvent;
import java.util.Map;

import com.google.common.collect.Maps;

import cn.milai.ib.container.listener.Command;

public class KeyboardMap {

	private KeyboardMap() {
	}

	private static final Map<Integer, Command> MAPPING = Maps.newHashMap();

	static {
		MAPPING.put(KeyEvent.VK_P, Command.PAUSE);
		MAPPING.put(KeyEvent.VK_W, Command.UP);
		MAPPING.put(KeyEvent.VK_S, Command.DOWN);
		MAPPING.put(KeyEvent.VK_A, Command.LEFT);
		MAPPING.put(KeyEvent.VK_D, Command.RIGHT);
		MAPPING.put(KeyEvent.VK_J, Command.A);
	}

	/**
	 * 返回 keyCode 对应的 Command
	 * 若对应的 Command 不存在，返回 null
	 * @param keyCode
	 * @return
	 */
	public static Command find(int keyCode) {
		return MAPPING.get(keyCode);
	}

}
