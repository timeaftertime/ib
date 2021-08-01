package cn.milai.ib.container.plugin.ui;

import java.awt.image.BufferedImage;

import cn.milai.ib.container.plugin.ContainerPlugin;
import cn.milai.ib.container.plugin.metrics.MetrizablePlugin;

/**
 * 用于展示用于界面的 {@link ContainerPlugin}
 * @author milai
 * @date 2021.02.09
 */
public interface UIPlugin extends MetrizablePlugin {

	@Override
	default String getCategory() { return "ui"; }

	/**
	 * 设置将被显示的视野范围
	 * @param camera
	 */
	void setCamera(Camera camera);

	/**
	 * 获取容器当前使用的 {@link Camera }
	 * @return
	 */
	Camera getCamera();

	/**
	 * 获取指定 UI X 的实际 X
	 * @param x
	 * @return
	 */
	default double toRealX(double x) {
		return getCamera().toRealX(this, x);
	}

	/**
	 * 获取指定 UI Y 的实际 Y
	 * @param y
	 * @return
	 */
	default double toRealY(double y) {
		return getCamera().toRealY(this, y);
	}

	/**
	 * 获取当前容器实际宽度
	 * @return
	 */
	default int getW() { return container().getW(); }

	/**
	 * 获取当前容器实际高度
	 * @return
	 */
	default int getH() { return container().getH(); }

	/**
	 * 获取当前容器的显示宽度
	 * @return
	 */
	int getUIW();

	/**
	 * 获取当前容器的显示高度
	 * @return
	 */
	int getUIH();

	/**
	 * 获取（去掉上边框后）游戏 Content 高度
	 * @return
	 */
	int getUICH();

	/**
	 * 重新设置容器的显示宽度和高度
	 * @param width
	 * @param height
	 */
	default void resizeUI(int width, int height) {
		container().newSize(width, height);
	}

	/**
	 * 重新设置容器实际的、显示的宽度和高度为 width, height 并保持显示中心位置不变
	 * @param width
	 * @param height
	 */
	default void resizeWithUI(int width, int height) {
		container().newSize(width, height);
		resizeUI(width, height);
	}

	/**
	 * 获取当前应该展示 UI
	 * @return
	 */
	BufferedImage getUI();

}
