package cn.milai.ib.plugin.ui;

import cn.milai.ib.geometry.Size;

/**
 * 展示 UI 的荧幕
 * @author milai
 * @date 2022.01.23
 */
public interface Screen extends Size {

	/**
	 * 获取关联的 {@link UICrew}
	 * @return
	 */
	UICrew ui();

	/**
	 * 设置关联 {@link UICrew}
	 */
	void setUI(UICrew ui);

	/**
	 * 将 {@link Screen} X 坐标转换为 {@link UICrew} X 坐标
	 * @param x
	 * @return
	 */
	default double toUIX(double x) {
		return x * ui().getW() / getW();
	}

	/**
	 * 将 {@link Screen} Y 坐标转换为 {@link UICrew} Y 坐标
	 * @param y
	 * @return
	 */
	default double toUIY(double y) {
		return y * ui().getH() / getH();
	}

	/**
	 * 初始化时调用
	 */
	default void onInit() {
	}

	/**
	 * 接受 {@link UICrew} 刷新通知
	 * @param ui
	 */
	default void onRefreshed() {
	}

	/**
	 * 接受 {@link UICrew} 大小修改通知
	 */
	default void onResized() {
	}

	/**
	 * 接受 {@link UICrew} 位置修改通知
	 */
	default void onRelocationed() {
	}

	/**
	 * {@link UICrew} 销毁后调用
	 */
	default void onDestroyed() {
	}

}
