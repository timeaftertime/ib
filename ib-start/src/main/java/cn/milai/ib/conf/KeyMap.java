package cn.milai.ib.conf;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import cn.milai.ib.form.Command;

/**
 * 键位设置
 *
 * 2019.11.20
 *
 * @author milai
 */
public class KeyMap {

	private static final Map<Integer, Command> keySettings = new HashMap<>();

	static {
		initKeySetting();
	}

	private static void initKeySetting() {
		keySettings.put(KeyEvent.VK_W, Command.UP);
		keySettings.put(KeyEvent.VK_S, Command.DOWN);
		keySettings.put(KeyEvent.VK_A, Command.LEFT);
		keySettings.put(KeyEvent.VK_D, Command.RIGHT);
		keySettings.put(KeyEvent.VK_J, Command.SHOOT);
	}

	public static Command commandOf(int keyCode) {
		return keySettings.get(keyCode);
	}

}
