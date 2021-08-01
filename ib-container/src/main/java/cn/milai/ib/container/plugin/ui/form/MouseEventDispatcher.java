package cn.milai.ib.container.plugin.ui.form;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import cn.milai.ib.container.plugin.control.CmdDispatcher;
import cn.milai.ib.container.pluginable.PluginableContainer;

/**
 * 鼠标点击事件分发器
 * @author milai
 * @date 2020.03.25
 */
public class MouseEventDispatcher extends MouseAdapter implements CmdDispatcher {

	private FormUIPlugin ui;
	private MouseMapping mouseMapping;

	/**
	 * 创建一个监听 {@link FormUIPlugin} 并分发 {@link MouseEvent} 的鼠标事件分发器
	 * @param ui
	 */
	public MouseEventDispatcher(FormUIPlugin ui, MouseMapping mouseMapping) {
		this.ui = ui;
		this.mouseMapping = mouseMapping;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		dispatch(mouseMapping.clicked(toReal(e)));
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		dispatch(mouseMapping.moved(toReal(e)));
	}

	@Override
	public void mousePressed(MouseEvent e) {
		dispatch(mouseMapping.pressed(toReal(e)));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		dispatch(mouseMapping.released(toReal(e)));
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		dispatch(mouseMapping.dragged(toReal(e)));
	}

	private MouseEvent toReal(MouseEvent e) {
		return new MouseEvent(
			(Component) e.getSource(),
			e.getID(), e.getWhen(), e.getModifiers(),
			(int) ui.getCamera().toRealX(ui, e.getX()), (int) ui.getCamera().toRealX(ui, e.getY()),
			e.getXOnScreen(), e.getYOnScreen(),
			e.getClickCount(), e.isPopupTrigger(), e.getButton()
		);
	}

	@Override
	public PluginableContainer getTargetContainer() { return ui.container(); }
}