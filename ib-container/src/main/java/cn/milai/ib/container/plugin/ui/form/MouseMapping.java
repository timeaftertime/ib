package cn.milai.ib.container.plugin.ui.form;

import java.awt.event.MouseEvent;

import cn.milai.ib.container.plugin.control.cmd.Cmd;

/**
 * 鼠标映射
 * @author milai
 * @date 2021.05.12
 */
public interface MouseMapping {

	/**
	 * 获取指定点击(按下后松开) {@link MouseEvent} 对应的 {@link Cmd}，若不存在映射，返回 null
	 * @param e
	 * @return
	 */
	default Cmd clicked(MouseEvent e) {
		return null;
	}

	/**
	 * 获取指定按下 {@link MouseEvent} 对应的 {@link Cmd}，若不存在映射，返回 null
	 * @param e
	 * @return
	 */
	default Cmd pressed(MouseEvent e) {
		return null;
	}

	/**
	 * 获取指定松开 {@link MouseEvent} 对应的 {@link Cmd}，若不存在映射，返回 null
	 * @param e
	 * @return
	 */
	default Cmd released(MouseEvent e) {
		return null;
	}

	/**
	 * 获取指定移动 {@link MouseEvent} 对应的 {@link Cmd}，若不存在映射，返回 null
	 * @param e
	 * @return
	 */
	default Cmd moved(MouseEvent e) {
		return null;
	}

	/**
	 * 获取指定拖动 {@link MouseEvent} 对应的 {@link Cmd}，若不存在映射，返回 null
	 * @param e
	 * @return
	 */
	default Cmd dragged(MouseEvent e) {
		return null;
	}

}
