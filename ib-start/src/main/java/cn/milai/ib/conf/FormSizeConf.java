package cn.milai.ib.conf;

import cn.milai.ib.conf.SystemConf;

public final class FormSizeConf {

	private FormSizeConf() {
	}

	public static final int BATTLE_WIDTH = SystemConf.prorate(792);
	public static final int BATTLE_HEIGHT = SystemConf.prorate(984);

	public static final int START_WIDTH = SystemConf.prorate(600);
	public static final int START_HEIGHT = SystemConf.prorate(780);

	public static final int LOGIN_WIDTH = SystemConf.prorate(600);
	public static final int LOGIN_HEIGHT = SystemConf.prorate(420);
	
	public static final int REGISTER_WIDTH = SystemConf.prorate(600);
	public static final int REGISTER_HEIGHT = SystemConf.prorate(420);

	public static final int ONLINE_WIDTH = SystemConf.prorate(360);
	public static final int ONLINE_HEIGHT = SystemConf.prorate(840);

}
