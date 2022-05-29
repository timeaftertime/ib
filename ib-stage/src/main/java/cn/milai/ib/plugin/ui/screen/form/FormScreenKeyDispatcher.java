package cn.milai.ib.plugin.ui.screen.form;

import java.awt.event.KeyEvent;

import cn.milai.ib.plugin.control.StageCmdDispatcher;
import cn.milai.ib.stage.Stage;

/**
 * 键盘事件分发器
 * @author milai
 * @date 2020.03.25
 */
public class FormScreenKeyDispatcher extends KeyDispatcher implements StageCmdDispatcher {

	private FormScreen formScreen;

	/**
	 * 创建一个监听 {@link FormScreen} 并根据 {@link KeyMapping} 分发 {@link KeyEvent} 的键盘事件分发器
	 * @param formScreen
	 */
	public FormScreenKeyDispatcher(FormScreen formScreen) {
		this.formScreen = formScreen;
	}

	@Override
	public Stage stage() {
		return formScreen.ui().stage();
	}

	@Override
	protected KeyMapping keyMapping() {
		return formScreen.getKeyMapping();
	}

}