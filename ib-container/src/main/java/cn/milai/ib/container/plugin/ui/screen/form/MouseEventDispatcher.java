package cn.milai.ib.container.plugin.ui.screen.form;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import cn.milai.ib.container.plugin.control.CmdDispatcher;
import cn.milai.ib.container.plugin.control.cmd.Cmd;

/**
 * 鼠标点击事件分发器
 * @author milai
 * @date 2020.03.25
 */
public class MouseEventDispatcher extends MouseAdapter implements CmdDispatcher {

	private FormScreen formScreen;

	/**
	 * 创建一个监听 {@link FormScreen} 并分发 {@link MouseEvent} 的鼠标事件分发器
	 * @param formScreen
	 */
	public MouseEventDispatcher(FormScreen formScreen) {
		this.formScreen = formScreen;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		dispatch(formScreen.getMouseMapping().clicked(toReal(e)));
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		dispatch(formScreen.getMouseMapping().moved(toReal(e)));
	}

	@Override
	public void mousePressed(MouseEvent e) {
		dispatch(formScreen.getMouseMapping().pressed(toReal(e)));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		dispatch(formScreen.getMouseMapping().released(toReal(e)));
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		dispatch(formScreen.getMouseMapping().dragged(toReal(e)));
	}

	/**
	 * 将视图事件转换为实际事件
	 * @param e
	 * @return
	 */
	protected MouseEvent toReal(MouseEvent e) {
		double uiX = formScreen.toUIX(e.getX());
		double uiY = formScreen.toUIY(e.getY());
		return new MouseEvent(
			(Component) e.getSource(),
			e.getID(), e.getWhen(), e.getModifiers(),
			(int) formScreen.ui().toRealX(uiX), (int) formScreen.ui().toRealX(uiY),
			e.getXOnScreen(), e.getYOnScreen(),
			e.getClickCount(), e.isPopupTrigger(), e.getButton()
		);
	}

	private void dispatch(Cmd cmd) {
		dispatch(formScreen.ui().container(), cmd);
	}

}