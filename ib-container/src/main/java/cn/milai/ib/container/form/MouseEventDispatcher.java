package cn.milai.ib.container.form;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.google.common.collect.Lists;

import cn.milai.ib.container.listener.Command;
import cn.milai.ib.obj.Controllable;

/**
 * AbstactFormContainer 的鼠标点击事件分发器
 * @author milai
 * @date 2020.03.25
 */
public class MouseEventDispatcher extends MouseAdapter {

	private AbstractFormContainer container;

	MouseEventDispatcher(AbstractFormContainer container) {
		this.container = container;
	}

	private Controllable getTarget(MouseEvent e) {
		for (Controllable controllable : Lists.newArrayList(container.getControllables())) {
			if (controllable.containsPoint(e.getX(), e.getY())) {
				return controllable;
			}
		}
		return null;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Controllable target = getTarget(e);
		if (target != null) {
			target.onReceive(Command.PRESSED);
		}
	}
}