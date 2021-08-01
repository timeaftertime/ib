package cn.milai.ib.container.plugin.ui.form;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import cn.milai.ib.container.plugin.control.CmdDispatcher;
import cn.milai.ib.container.pluginable.PluginableContainer;

/**
 * 键盘事件分发器
 * @author milai
 * @date 2020.03.25
 */
public class KeyEventDispatcher extends KeyAdapter implements CmdDispatcher {

	private FormUIPlugin ui;
	private KeyMapping keyMapping;

	/**
	 * 创建一个监听 {@link FormUIPlugin} 并 根据 {@link KeyMapping} 分发 {@link KeyEvent} 的键盘事件分发器
	 * @param ui
	 * @param keyMapping
	 */
	public KeyEventDispatcher(FormUIPlugin ui, KeyMapping keyMapping) {
		this.ui = ui;
		this.keyMapping = keyMapping;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		dispatch(keyMapping.pressed(e));
	}

	@Override
	public void keyReleased(KeyEvent e) {
		dispatch(keyMapping.released(e));
	}

	@Override
	public void keyTyped(KeyEvent e) {
		dispatch(keyMapping.typed(e));
	}

	@Override
	public PluginableContainer getTargetContainer() { return ui.container(); }
}