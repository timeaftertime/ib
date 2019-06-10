package cn.milai.ib.client.game.conf;

public class SystemConf {

	// 整个游戏的比例
	public static final double SIZE_RATIO = 1.1;
	
	public static int prorate(int value) {
		return (int) (value * SIZE_RATIO);
	}
}
