package cn.milai.ib.container.plugin.ui.screen.form;

import javax.swing.JFrame;

import cn.milai.ib.container.plugin.ui.Screen;

/**
 * 窗口剧本容器
 * 2019.12.06
 * @author milai
 */
public interface FormScreen extends Screen {

	/**
	 * 获取 {@link MouseMapping}
	 * @return
	 */
	MouseMapping getMouseMapping();

	/**
	 * 设置 {@link MouseMapping}
	 * @param mapping
	 */
	void setMouseMapping(MouseMapping mapping);

	/**
	 * 获取 {@link KeyMapping}
	 * @return
	 */
	KeyMapping getKeyMapping();

	/**
	 * 设置 {@link KeyMapping}
	 * @param mapping
	 */
	void setKeyMapping(KeyMapping mapping);

	/**
	 * 获取容器对应的窗口
	 * @return
	 */
	JFrame form();

}
