package cn.milai.ib.container.ui.form;

import java.awt.event.KeyEvent;
import java.util.Map;

import com.google.common.collect.Maps;

import cn.milai.ib.container.control.CommandType;

/**
 * 键盘映射 临时方案
 * @author milai
 */
public class KeyboardMap {

	private KeyboardMap() {
	}

	private static final Map<Integer, CommandType> SET_MAPPING = Maps.newHashMap();
	private static final Map<Integer, CommandType> UNSET_MAPPING = Maps.newHashMap();

	static {
		SET_MAPPING.put(KeyEvent.VK_W, CommandType.UP);
		SET_MAPPING.put(KeyEvent.VK_S, CommandType.DOWN);
		SET_MAPPING.put(KeyEvent.VK_A, CommandType.LEFT);
		SET_MAPPING.put(KeyEvent.VK_D, CommandType.RIGHT);
		SET_MAPPING.put(KeyEvent.VK_J, CommandType.A);

		UNSET_MAPPING.put(KeyEvent.VK_W, CommandType.U_UP);
		UNSET_MAPPING.put(KeyEvent.VK_S, CommandType.U_DOWN);
		UNSET_MAPPING.put(KeyEvent.VK_A, CommandType.U_LEFT);
		UNSET_MAPPING.put(KeyEvent.VK_D, CommandType.U_RIGHT);
		UNSET_MAPPING.put(KeyEvent.VK_J, CommandType.U_A);
	}

	/**
	 * 返回按下 keyCode 时对应的 Command
	 * 若对应的 Command 不存在，返回 null
	 * @param keyCode
	 * @return
	 */
	public static CommandType findSet(int keyCode) {
		return SET_MAPPING.get(keyCode);
	}

	/**
	 * 返回松开 ketCode 时对应的 Command
	 * 若对应的 Command 不存在，返回 null
	 * @param keyCode
	 * @return
	 */
	public static CommandType findUnset(int keyCode) {
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
