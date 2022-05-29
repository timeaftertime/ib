package cn.milai.ib.actor.nature;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import cn.milai.ib.actor.Actor;

/**
 * {@link Actor} 绘制器
 * @author milai
 */
public interface Painter extends Nature {

	String NAME = "painter";

	@Override
	default String name() {
		return NAME;
	}

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
