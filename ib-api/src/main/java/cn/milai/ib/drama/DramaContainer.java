package cn.milai.ib.drama;

import cn.milai.ib.container.Container;
import cn.milai.ib.container.LifecycleContainer;
import cn.milai.ib.container.UIContainer;

/**
 * {@link Drama } 中用到的 {@link Container } 
 * @author milai
 * @date 2020.12.02
 */
public interface DramaContainer extends LifecycleContainer, UIContainer {

	/**
	 * 保存当前容器的实际显示宽度和高度到实际栈，并设置当前实际宽度高度为新值
	 * @param width
	 * @param height
	 */
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
