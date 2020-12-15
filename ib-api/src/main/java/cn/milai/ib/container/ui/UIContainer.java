package cn.milai.ib.container.ui;

import java.awt.image.BufferedImage;

import cn.milai.ib.container.Container;

/**
 * 可展示用户界面的容器
 * @author milai
 * @date 2020.11.29
 */
public interface UIContainer extends Container {

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
	 * 获取容器实际宽度
	 * @return
	 */
	int getWidth();

	/**
	 * 获取容器实际长度
	 * @return
	 */
	int getHeight();

	/**
	 * 获取当前容器的显示宽度
	 * @return
	 */
	int getUIWidth();

	/**
	 * 获取当前容器的显示高度
	 * @return
	 */
	int getUIHeight();

	/**
	 * 获取（去掉上边框后）游戏 Content 高度
	 * @return
	 */
	int getUICHeight();

	/**
	 * 重新设置容器的宽度和高度
	 * @param width
	 * @param height
	 */
	void resize(int width, int height);

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
		resize(width, height);
		resizeUI(width, height);
	}

	/**
	 * 获取当前容器（被 {@link Camera}转换之前）的显示图片
	 * @return
	 */
	BufferedImage getUI();

}
