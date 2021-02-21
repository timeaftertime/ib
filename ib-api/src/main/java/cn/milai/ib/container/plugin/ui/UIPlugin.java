package cn.milai.ib.container.plugin.ui;

import java.awt.image.BufferedImage;

import cn.milai.ib.container.plugin.ContainerPlugin;

/**
 * 用于展示用于界面的 {@link ContainerPlugin}
 * @author milai
 * @date 2021.02.09
 */
public interface UIPlugin extends ContainerPlugin {

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
	 * 获取当前容器实际宽度
	 * @return
	 */
	int getW();

	/**
	 * 获取当前容器实际高度
	 * @return
	 */
	int getH();

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
	 * 重新设置容器的显示宽度和高度并使中心位置回到屏幕中心
	 * @param width
	 * @param height
	 */
	void resizeUI(int width, int height);

	/**
	 * 重新设置容器实际的、显示的宽度和高度为 width, height 并保持显示中心位置不变
	 * @param width
	 * @param height
	 */
	default void resizeWithUI(int width, int height) {
		getContainer().newSize(width, height);
		resizeUI(width, height);
	}

	/**
	 * 线程安全地获取当前容器（被 {@link Camera}转换之前）的显示图片
	 * @return
	 */
	BufferedImage getNowImage();

	/**
	 * 设置背景图片
	 * @param img
	 */
	void setBackgroud(Image img);

}
