package cn.milai.ib.container.plugin.ui.screen.form;

import java.awt.event.KeyEvent;

import cn.milai.ib.container.plugin.control.cmd.Cmd;

/**
 * 键盘映射
 * @author milai
 * @date 2021.05.12
 */
public interface KeyMapping {

	/**
	 * 获取指定按下事件的指令，若不存在对应指令映射，返回 null
	 * @param e
	 * @return
	 */
	default Cmd pressed(KeyEvent e) {
		return null;
	}

	/**
	 * 获取指定释放事件的指令，若不存在对应指令映射，返回 null
	 * @param e
	 * @return
	 */
	default Cmd released(KeyEvent e) {
		return null;
	}

	/**
	 * 获取指定按下并释放事件的指令，若不存在对应指令映射，返回 null
	 * @param e
	 * @return
	 */
	default Cmd typed(KeyEvent e) {
		return null;
	}
}
