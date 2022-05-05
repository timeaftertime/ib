package cn.milai.ib.container.plugin.ui;

import java.awt.image.BufferedImage;

import cn.milai.ib.container.Container;
import cn.milai.ib.container.plugin.ContainerPlugin;
import cn.milai.ib.container.plugin.ExclusiveContainerPlugin;
import cn.milai.ib.geometry.slot.BoundsSlot;

/**
 * 用于展示用于界面的 {@link ContainerPlugin}
 * @author milai
 * @date 2021.02.09
 */
public interface UIPlugin extends BoundsSlot, ExclusiveContainerPlugin {

	/**
	 * 获取指定 UI X 的实际 X
	 * @param x
	 * @return
	 */
	default double toRealX(double x) {
		return getX() + x;
	}

	/**
	 * 获取指定 UI Y 的实际 Y
	 * @param y
	 * @return
	 */
	default double toRealY(double y) {
		return getY() + y;
	}

	/**
	 * 获取当前应该展示 UI
	 * @return
	 */
	BufferedImage getUI();

	/**
	 * 设置背景
	 * @param img
	 */
	void setBackgroud(Image img);

	/**
	 * 添加一个 {@link Screen}。
	 * 若当前已经关联到 {@link Container}，将立即调用 {@link Screen#onPlug}
	 * @param screen
	 */
	void addScreen(Screen screen);

	/**
	  获取标题。标题是否显示由具体 {@link Screen} 决定 
	 * @return
	 */
	String getTitle();

	/**
	 * 设置标题。标题是否显示由具体 {@link Screen} 决定 
	 */
	void setTitle(String title);

}
