package cn.milai.ib.plugin.ui.screen.form;

import java.awt.Component;
import java.awt.event.MouseEvent;

import cn.milai.ib.plugin.control.StageCmdDispatcher;
import cn.milai.ib.stage.Stage;

/**
 * 鼠标点击事件分发器
 * @author milai
 * @date 2020.03.25
 */
public class FormScreenMouseDispatcher extends MouseDispatcher implements StageCmdDispatcher {

	private FormScreen formScreen;

	/**
	 * 创建一个监听 {@link FormScreen} 并分发 {@link MouseEvent} 的鼠标事件分发器
	 * @param formScreen
	 */
	public FormScreenMouseDispatcher(FormScreen formScreen) {
		this.formScreen = formScreen;
	}

	/**
	 * 将视图事件转换为实际事件
	 * @param e
	 * @return
	 */
	@Override
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

	@Override
	protected MouseMapping mouseMapping() {
		return formScreen.getMouseMapping();
	}

	@Override
	public Stage stage() {
		return formScreen.ui().stage();
	}

}