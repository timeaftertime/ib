package cn.milai.ib.container;

import java.util.Stack;

import cn.milai.ib.container.DramaContainer;

/**
 * {@link DramaContainer} 的抽象实现
 * @author milai
 * @date 2020.12.05
 */
public abstract class AbstractDramaContainer extends CharacterAwareContainer implements DramaContainer {

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
		sizes.add(new Size(getWidth(), getHeight()));
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
		uiSizes.add(new Size(getUIWidth(), getUIHeight()));
		resizeUI(width, height);
	}

	@Override
	public void restoreUISize() {
		if (uiSizes.isEmpty()) {
			return;
		}
		Size size = uiSizes.pop();
		resizeUI(size.width, size.height);
	}

}
