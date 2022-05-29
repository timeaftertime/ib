package cn.milai.ib.plugin.ui.screen.form;

import java.awt.event.MouseEvent;

import cn.milai.ib.plugin.control.cmd.Cmd;
import cn.milai.ib.plugin.control.cmd.PointCmd;

/**
 * 携带 {@link MouseEvent} 位置的 {@link MouseMapping} 实现
 * @author milai
 * @date 2021.05.15
 */
public class MouseLocationMapping implements MouseMapping {

	@Override
	public final Cmd clicked(MouseEvent e) {
		return newPointCmd(clickedCmdType(), e);
	}

	@Override
	public final Cmd moved(MouseEvent e) {
		return newPointCmd(movedCmdTYpe(), e);
	}

	@Override
	public final Cmd pressed(MouseEvent e) {
		return newPointCmd(pressedCmdTYpe(), e);
	}

	@Override
	public final Cmd released(MouseEvent e) {
		return newPointCmd(releasedCmdTYpe(), e);
	}

	@Override
	public final Cmd dragged(MouseEvent e) {
		return newPointCmd(draggedCmdTYpe(), e);
	}

	private Cmd newPointCmd(int cmdType, MouseEvent e) {
		return new PointCmd(cmdType, e.getX(), e.getY());
	}

	/**
	 * 获取 click 类型指令 type
	 * @return
	 */
	protected int clickedCmdType() {
		return Cmd.CLICK;
	}

	/**
	 * 获取 click 类型指令 type
	 * @return
	 */
	protected int movedCmdTYpe() {
		return Cmd.MOVE;
	}

	/**
	 * 获取 click 类型指令 type
	 * @return
	 */
	protected int pressedCmdTYpe() {
		return Cmd.NOOP;
	}

	/**
	 * 获取 click 类型指令 type
	 * @return
	 */
	protected int releasedCmdTYpe() {
		return Cmd.NOOP;
	}

	/**
	 * 获取 click 类型指令 type
	 * @return
	 */
	protected int draggedCmdTYpe() {
		return Cmd.NOOP;
	}
}
