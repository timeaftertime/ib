package cn.milai.ib.plugin.ui.screen.form;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.util.Assert;

import cn.milai.ib.plugin.control.cmd.Cmd;

/**
 * {@link KeyMapping} 默认实现
 * @author milai
 * @date 2021.05.12
 */
public class KeyCodeMapping implements KeyMapping {

	private Map<Integer, Supplier<Cmd>> pressedMap = new HashMap<>();
	private Map<Integer, Supplier<Cmd>> releasedMap = new HashMap<>();
	private Map<Integer, Supplier<Cmd>> typedMap = new HashMap<>();

	private static final Supplier<Cmd> NULL_SUPPLIER = () -> null;

	private Cmd getCmdFromMap(Map<Integer, Supplier<Cmd>> m, KeyEvent e) {
		return m.getOrDefault(e.getKeyCode(), NULL_SUPPLIER).get();
	}

	@Override
	public final Cmd pressed(KeyEvent e) {
		return getCmdFromMap(pressedMap, e);
	}

	@Override
	public final Cmd released(KeyEvent e) {
		return getCmdFromMap(releasedMap, e);
	}

	@Override
	public final Cmd typed(KeyEvent e) {
		return getCmdFromMap(typedMap, e);
	}

	/**
	 * 设置指定 {@code keyCode } 被按下时的指令 {@link Supplier} 并返回当前 {@link KeyCodeMapping}
	 * @param keyCode
	 * @param s
	 * @return
	 */
	public KeyCodeMapping onPressed(int keyCode, Supplier<Cmd> s) {
		putMapping(pressedMap, keyCode, s);
		return this;
	}

	/**
	 * 设置指定 {@code keyCode } 被松开时的指令 {@link Supplier} 并返回当前 {@link KeyCodeMapping}
	 * @param keyCode
	 * @param s
	 * @return
	 */
	public KeyCodeMapping onReleased(int keyCode, Supplier<Cmd> s) {
		putMapping(releasedMap, keyCode, s);
		return this;
	}

	/**
	 * 设置指定 {@code keyCode } 输入字符时的指令 {@link Supplier} 并返回当前 {@link KeyCodeMapping}
	 * @param keyCode
	 * @param s
	 * @return
	 */
	public KeyCodeMapping onTyped(int keyCode, Supplier<Cmd> s) {
		putMapping(typedMap, keyCode, s);
		return this;
	}

	private void putMapping(Map<Integer, Supplier<Cmd>> m, int keyCode, Supplier<Cmd> s) {
		Assert.notNull(s, "指令 Supplier 不能为 null");
		m.put(keyCode, s);
	}

}
