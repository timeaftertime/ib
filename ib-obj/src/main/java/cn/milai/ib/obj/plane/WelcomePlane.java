package cn.milai.ib.obj.plane;

import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.container.Container;

public class WelcomePlane extends EnemyPlane {

	private static final int WIDTH = SystemConf.prorate(72);
	private static final int HEIGHT = SystemConf.prorate(48);
	private static final int SPEED_Y = SystemConf.prorate(5);

	private static final int INIT_LIFE = 1;
	private static final int SCORE = 1;

	public WelcomePlane(int x, int y, Container container) {
		super(x, y, WIDTH, HEIGHT, 0, SPEED_Y, 0, INIT_LIFE, null, container);
	}

	@Override
	public int getScore() {
		return SCORE;
	}

	@Override
	protected void beforeMove() {

	}

	@Override
	protected void afterMove() {
		removeIfOutOfOwner();
	}

	private void removeIfOutOfOwner() {
		if (getY() > getContainer().getHeight())
			getContainer().removeGameObject(this);
	}

}
