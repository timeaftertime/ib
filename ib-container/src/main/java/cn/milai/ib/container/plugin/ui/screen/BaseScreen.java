package cn.milai.ib.container.plugin.ui.screen;

import cn.milai.ib.container.plugin.ui.Screen;
import cn.milai.ib.container.plugin.ui.UIPlugin;

/**
 * {@link Screen} 抽象实现
 * @author milai
 * @date 2022.03.06
 */
public abstract class BaseScreen implements Screen {

	private UIPlugin ui;

	public BaseScreen() {
	}

	@Override
	public void setUI(UIPlugin ui) { this.ui = ui; }

	@Override
	public UIPlugin ui() {
		return ui;
	}

}
