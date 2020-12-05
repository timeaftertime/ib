package cn.milai.ib.container.form;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.google.common.collect.Lists;

import cn.milai.ib.character.Controllable;
import cn.milai.ib.container.Camera;
import cn.milai.ib.container.listener.Command;

/**
 * {@link AbstractFormContainer} 的鼠标点击事件分发器
 * @author milai
 * @date 2020.03.25
 */
public class MouseEventDispatcher extends MouseAdapter {

	private AbstractFormContainer c;

	MouseEventDispatcher(AbstractFormContainer container) {
		this.c = container;
	}

	private Controllable getTarget(int x, int y) {
		for (Controllable controllable : Lists.newArrayList(c.getControllables())) {
			if (controllable.containsPoint(x, y)) {
				return controllable;
			}
		}
		return null;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Camera camera = c.getCamera();
		Controllable target = getTarget(camera.toRealX(c, e.getX()), camera.toRealY(c, e.getY()));
		if (target != null) {
			target.onReceive(Command.PRESSED);
		}
	}
}