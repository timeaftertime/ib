package cn.milai.ib.obj.helper;

import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.container.Container;
import cn.milai.ib.obj.plane.Plane;

public class OneLifeHelper extends Helper {

	private static final int WIDTH = SystemConf.prorate(36);
	private static final int HEIGHT = SystemConf.prorate(36);
	private static final int SPEED_X = SystemConf.prorate(0);
	private static final int SPEED_Y = SystemConf.prorate(15);

	private static final int GAIN_LIFE = 1;
	private static final int INIT_LIFE = 1;

	public OneLifeHelper(int x, int y, Container container) {
		super(x, y, WIDTH, HEIGHT, SPEED_X, SPEED_Y, INIT_LIFE, container);
	}

	@Override
	public void makeFunction(Plane plane) {
		plane.gainLife(GAIN_LIFE);
	}

}
