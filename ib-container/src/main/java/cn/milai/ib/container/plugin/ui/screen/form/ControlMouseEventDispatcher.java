package cn.milai.ib.container.plugin.ui.screen.form;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import cn.milai.ib.container.plugin.control.cmd.Cmd;
import cn.milai.ib.container.pluginable.PluginableContainer;
import cn.milai.ib.control.Control;

/**
 * 根据映射将 {@link MouseEvent} 转发给指定 {@link Control} 列表中元素的 {@link MouseListener}
 * @author milai
 * @date 2022.03.17
 */
public class ControlMouseEventDispatcher extends MouseEventDispatcher {

	private Control[] controls;

	public ControlMouseEventDispatcher(Control[] controls, FormScreen formScreen) {
		super(formScreen);
		this.controls = controls;
	}

	@Override
	public void dispatch(PluginableContainer container, Cmd cmd) {
		for (Control c : controls) {
			if (!c.exec(cmd)) {
				break;
			}
		}
	}

}
