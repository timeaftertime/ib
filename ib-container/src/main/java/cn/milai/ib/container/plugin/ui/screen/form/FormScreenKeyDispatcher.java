package cn.milai.ib.container.plugin.ui.screen.form;

import java.awt.event.KeyEvent;

import cn.milai.ib.container.plugin.control.ContainerCmdDispatcher;
import cn.milai.ib.container.pluginable.PluginableContainer;

/**
 * 键盘事件分发器
 * @author milai
 * @date 2020.03.25
 */
public class FormScreenKeyDispatcher extends KeyDispatcher implements ContainerCmdDispatcher {

	private FormScreen formScreen;

	/**
	 * 创建一个监听 {@link FormScreen} 并根据 {@link KeyMapping} 分发 {@link KeyEvent} 的键盘事件分发器
	 * @param formScreen
	 */
	public FormScreenKeyDispatcher(FormScreen formScreen) {
		this.formScreen = formScreen;
	}

	@Override
	public PluginableContainer container() {
		return formScreen.ui().container();
	}

	@Override
	protected KeyMapping keyMapping() {
		return formScreen.getKeyMapping();
	}

}