package cn.milai.ib.plugin.ui.screen;

import cn.milai.ib.plugin.ui.Screen;
import cn.milai.ib.plugin.ui.UICrew;

/**
 * {@link Screen} 抽象实现
 * @author milai
 * @date 2022.03.06
 */
public abstract class BaseScreen implements Screen {

	private UICrew ui;

	public BaseScreen() {
	}

	@Override
	public void setUI(UICrew ui) { this.ui = ui; }

	@Override
	public UICrew ui() {
		return ui;
	}

}
