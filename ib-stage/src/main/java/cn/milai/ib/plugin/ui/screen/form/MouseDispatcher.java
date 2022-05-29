package cn.milai.ib.plugin.ui.screen.form;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Function;

import cn.milai.ib.plugin.control.CmdDispatcher;
import cn.milai.ib.plugin.control.StageCmdDispatcher;
import cn.milai.ib.plugin.control.cmd.Cmd;

/**
 * 将 {@link MouseEvent} 转换为 {@link Cmd} 并分发的 {@link StageCmdDispatcher} 
 * @author milai
 * @date 2022.04.17
 */
public abstract class MouseDispatcher extends MouseAdapter implements CmdDispatcher {

	@Override
	public void mouseClicked(MouseEvent e) {
		dispatch(m -> (event -> m.clicked(event)), e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		dispatch(m -> (event -> m.moved(event)), e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		dispatch(m -> (event -> m.pressed(event)), e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		dispatch(m -> (event -> m.released(event)), e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		dispatch(m -> (event -> m.dragged(event)), e);
	}

	private void dispatch(Function<MouseMapping, Function<MouseEvent, Cmd>> fun, MouseEvent e) {
		MouseEvent real = toReal(e);
		if (dispatch(fun.apply(mouseMapping()).apply(real))) {
			e.consume();
		}
	}

	/**
	 * 将视图事件转换为实际事件
	 * @param e
	 * @return
	 */
	protected MouseEvent toReal(MouseEvent e) {
		return e;
	}

	/**
	 * 获取 {@link MouseMapping}
	 * @return
	 */
	abstract protected MouseMapping mouseMapping();

}
