package cn.milai.ib.client.game.conf;

public class SystemConf {

	// 整个游戏的尺寸比例
	public static final double SIZE_RATIO = 1.2;
	// 整个游戏的速度比例
	public static final double SPEED_RATIO = 1.0;
	
	public static int sizeProrate(int value) {
		return (int) (value * SIZE_RATIO);
	}
	
	public static int speedProrate(int value) {
		return (int) (value * SPEED_RATIO);
	}
}
