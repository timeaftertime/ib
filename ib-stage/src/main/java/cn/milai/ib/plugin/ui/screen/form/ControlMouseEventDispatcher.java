package cn.milai.ib.plugin.ui.screen.form;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import cn.milai.ib.actor.prop.Prop;
import cn.milai.ib.plugin.control.cmd.Cmd;

/**
 * 根据映射将 {@link MouseEvent} 转发给指定 {@link Prop} 列表中元素的 {@link MouseListener}
 * @author milai
 * @date 2022.03.17
 */
public class ControlMouseEventDispatcher extends MouseDispatcher {

	private MouseMapping mouseMapping;
	private Prop[] controls;

	public ControlMouseEventDispatcher(Prop[] controls) {
		this.controls = controls;
		mouseMapping = new MouseLocationMapping();
	}

	@Override
	protected MouseMapping mouseMapping() {
		return mouseMapping;
	}

	@Override
	public boolean dispatch(Cmd cmd) {
		for (Prop c : controls) {
			if (!c.exec(cmd)) {
				return true;
			}
		}
		return false;
	}

}
