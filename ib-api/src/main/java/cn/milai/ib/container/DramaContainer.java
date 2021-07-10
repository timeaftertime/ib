package cn.milai.ib.container;

import cn.milai.ib.container.pluginable.media.MediaContainer;
import cn.milai.ib.container.pluginable.ui.UIContainer;
import cn.milai.ib.mode.drama.Drama;

/**
 * {@link Drama } 中用到的 {@link Container } 
 * @author milai
 * @date 2020.12.02
 */
public interface DramaContainer extends UIContainer, MediaContainer {

	/**
	 * 保存当前容器的实际显示宽度和高度到实际栈，并设置当前实际宽度高度为新值
	 * @param width
	 * @param height
	 */
	@Override
	void newSize(int width, int height);

	/**
	 * 还原当前容器的显示宽度和高度为实际栈上最后保存的宽度和高度
	 */
	void restoreSize();

	/**
	 * 保存当前容器的显示宽度和高度到显示栈，并设置当前显示宽度高度为新值
	 * @param width
	 * @param height
	 */
	void newUISize(int width, int height);

	/**
	 * 还原当前容器的显示宽度和高度为显示栈上最后保存的宽度和高度
	 * 若栈为空，则不修改
	 */
	void restoreUISize();

}
