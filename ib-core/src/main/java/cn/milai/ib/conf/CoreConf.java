package cn.milai.ib.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Infinity Battle 核心模块配置
 * @author milai
 * @date 2021.02.28
 */
@Component
@ConfigurationProperties(prefix = "ib.core")
public class CoreConf {

	/**
	 * 整个游戏的速度比例，帧间隔时间应乘以该常量
	 */
	private double speed = 1.0;

	/**
	 * GIF 图片更新到下一张所间隔的帧数
	 */
	private int imageUpdateFrame = 3;

	public double getSpeed() { return speed; }

	public void setSpeed(double speed) { this.speed = speed; }

	public int getImageUpdateFrame() { return imageUpdateFrame; }

	public void setImageUpdateFrame(int imageUpdateFrame) { this.imageUpdateFrame = imageUpdateFrame; }

}
