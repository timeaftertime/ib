package cn.milai.ib.container.plugin.ui.form;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import cn.milai.ib.container.plugin.control.cmd.CmdType;

/**
 * 键盘映射 临时方案
 * @author milai
 */
public class KeyboardMap {

	private KeyboardMap() {}

	private static final Map<Integer, CmdType> SET_MAPPING = new HashMap<>();
	private static final Map<Integer, CmdType> UNSET_MAPPING = new HashMap<>();

	static {
		SET_MAPPING.put(KeyEvent.VK_W, CmdType.UP);
		SET_MAPPING.put(KeyEvent.VK_S, CmdType.DOWN);
		SET_MAPPING.put(KeyEvent.VK_A, CmdType.LEFT);
		SET_MAPPING.put(KeyEvent.VK_D, CmdType.RIGHT);
		SET_MAPPING.put(KeyEvent.VK_J, CmdType.A);

		UNSET_MAPPING.put(KeyEvent.VK_W, CmdType.U_UP);
		UNSET_MAPPING.put(KeyEvent.VK_S, CmdType.U_DOWN);
		UNSET_MAPPING.put(KeyEvent.VK_A, CmdType.U_LEFT);
		UNSET_MAPPING.put(KeyEvent.VK_D, CmdType.U_RIGHT);
		UNSET_MAPPING.put(KeyEvent.VK_J, CmdType.U_A);
	}

	/**
	 * 返回按下 keyCode 时对应的 Command
	 * 若对应的 Command 不存在，返回 null
	 * @param keyCode
	 * @return
	 */
	public static CmdType findSet(int keyCode) {
		return SET_MAPPING.get(keyCode);
	}

	/**
	 * 返回松开 ketCode 时对应的 Command
	 * 若对应的 Command 不存在，返回 null
	 * @param keyCode
	 * @return
	 */
	public static CmdType findUnset(int keyCode) {
		return UNSET_MAPPING.get(keyCode);
	}

	/**
	 * 判断 keyCode 是否为暂停按钮
	 * @param keyCode
	 * @return
	 */
	public static boolean isPause(int keyCode) {
		return keyCode == KeyEvent.VK_P;
	}

}
