package cn.milai.ib.container.plugin.ui.screen.form;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import cn.milai.ib.container.plugin.control.CmdDispatcher;
import cn.milai.ib.container.plugin.control.cmd.Cmd;

/**
 * 键盘事件分发器
 * @author milai
 * @date 2020.03.25
 */
public class KeyEventDispatcher extends KeyAdapter implements CmdDispatcher {

	private FormScreen formScreen;

	/**
	 * 创建一个监听 {@link FormScreen} 并根据 {@link KeyMapping} 分发 {@link KeyEvent} 的键盘事件分发器
	 * @param ui
	 */
	public KeyEventDispatcher(FormScreen ui) {
		this.formScreen = ui;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		dispatch(formScreen.getKeyMapping().pressed(e));
	}

	@Override
	public void keyReleased(KeyEvent e) {
		dispatch(formScreen.getKeyMapping().released(e));
	}

	@Override
	public void keyTyped(KeyEvent e) {
		dispatch(formScreen.getKeyMapping().typed(e));
	}

	private void dispatch(Cmd cmd) {
		dispatch(formScreen.ui().container(), cmd);
	}

}