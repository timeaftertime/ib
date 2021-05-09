package cn.milai.ib.container.plugin.printer;

import java.awt.image.BufferedImage;

import cn.milai.ib.container.Container;
import cn.milai.ib.container.plugin.metrics.MetrizablePlugin;
import cn.milai.ib.container.plugin.ui.Image;

/**
 * {@link Container} 打印插件，用于将 {@link Container} 转换为 {@link BufferedImage}
 * @author milai
 * @date 2021.05.08
 */
public interface PrinterPlugin extends MetrizablePlugin {

	@Override
	default String getCategory() { return "printer"; }

	/**
	 * 打印一次的延时
	 */
	String PRINT_DELAY = "print";

	/**
	 * 获取打印当前 {@link Container} 的 {@link BufferedImage}
	 * @return
	 */
	BufferedImage print();

	/**
	 * 设置背景图片
	 * @param img
	 */
	void setBackgroud(Image img);
}
