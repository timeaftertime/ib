package cn.milai.ib.plugin.ui.screen.form;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Function;

import cn.milai.ib.plugin.control.CmdDispatcher;
import cn.milai.ib.plugin.control.cmd.Cmd;

/**
 * 分发 {@link KeyEvent} 的 {@link CmdDispatcher}
 * @author milai
 * @date 2022.04.17
 */
public abstract class KeyDispatcher extends KeyAdapter implements CmdDispatcher {

	@Override
	public void keyPressed(KeyEvent e) {
		dispatch(m -> (event -> m.pressed(event)), e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		dispatch(m -> (event -> m.released(event)), e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		dispatch(m -> (event -> m.typed(event)), e);
	}

	private void dispatch(Function<KeyMapping, Function<KeyEvent, Cmd>> fun, KeyEvent e) {
		if (dispatch(fun.apply(keyMapping()).apply(e))) {
			e.consume();
		}
	}

	/**
	 * 获取关联的 {@link KeyMapping}
	 * @return
	 */
	abstract protected KeyMapping keyMapping();

}
