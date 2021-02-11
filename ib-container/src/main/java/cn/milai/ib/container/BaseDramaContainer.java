package cn.milai.ib.container;

import java.util.Stack;

import cn.milai.ib.container.plugin.BasePluginableContainer;
import cn.milai.ib.container.plugin.ui.UIPlugin;

/**
 * {@link DramaContainer} 的抽象实现
 * @author milai
 * @date 2020.12.05
 */
public class BaseDramaContainer extends BasePluginableContainer implements DramaContainer {

	public BaseDramaContainer() {}

	private static class Size {
		int width;
		int height;

		public Size(int width, int height) {
			this.width = width;
			this.height = height;
		}
	}

	private Stack<Size> sizes = new Stack<>();
	private Stack<Size> uiSizes = new Stack<>();

	@Override
	public void newSize(int width, int height) {
		sizes.add(new Size(getW(), getH()));
		resize(width, height);
	}

	@Override
	public void restoreSize() {
		if (sizes.isEmpty()) {
			return;
		}
		Size size = sizes.pop();
		resize(size.width, size.height);
	}

	@Override
	public void newUISize(int width, int height) {
		UIPlugin ui = getPlugin(UIPlugin.class);
		if (ui == null) {
			return;
		}
		uiSizes.add(new Size(ui.getUIW(), ui.getUIH()));
		ui.resizeUI(width, height);
	}

	@Override
	public void restoreUISize() {
		if (uiSizes.isEmpty()) {
			return;
		}
		Size size = uiSizes.pop();
		fire(UIPlugin.class, ui -> ui.resizeUI(size.width, size.height));
	}

}
