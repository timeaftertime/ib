package cn.milai.ib.plugin.ui.screen.form.bar;

import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import cn.milai.ib.geometry.Position;
import cn.milai.ib.geometry.Size;
import cn.milai.ib.plugin.ui.screen.form.KeyDispatcher;
import cn.milai.ib.plugin.ui.screen.form.MouseDispatcher;

/**
 * {@link JFrame} 栏
 * @author milai
 * @date 2022.04.17
 */
public interface Bar extends Position, Size {

	/**
	 * 获取关联 {@link JFrame}
	 * @return
	 */
	JFrame getForm();

	/**
	 * 设置 {@link JFrame}
	 * @param form
	 */
	void setForm(JFrame form);

	/**
	 * 获取宽度
	 * @return
	 */
	double getW();

	/**
	 * 获取高度
	 * @return
	 */
	double getH();

	/**
	 *  绘制当前 {@link Bar}
	 * @param g
	 */
	void paintTo(BufferedImage base);

	/**
	 * 获取鼠标事件处理器
	 * @return
	 */
	default MouseDispatcher mouseDispatcher() {
		return null;
	}

	/**
	 * 获取键盘事件处理器
	 * @return
	 */
	default KeyDispatcher KeyDispatcher() {
		return null;
	}
}
