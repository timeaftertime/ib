package cn.milai.ib.item.property;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import cn.milai.ib.item.Item;

/**
 * {@link Item} 绘制器
 * @author milai
 */
public interface Painter extends Property {

	/**
	 * 属性名
	 */
	String NAME = "painter";

	/**
	 * 使用指定 {@link Graphics} 进行绘制
	 * @param g
	 */
	void paintWith(Graphics g);

	/**
	 * 获取下一帧需要绘制的图像
	 * @return
	 */
	BufferedImage getNowImage();

}
