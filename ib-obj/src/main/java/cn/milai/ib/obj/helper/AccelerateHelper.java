package cn.milai.ib.obj.helper;

import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.container.Container;
import cn.milai.ib.obj.plane.Plane;
import cn.milai.ib.obj.plane.PlayerPlane;

public class AccelerateHelper extends Helper {

	private static final int WIDTH = SystemConf.prorate(36);
	private static final int HEIGHT = SystemConf.prorate(54);
	private static final int SPEED_X = SystemConf.prorate(0);
	private static final int SPEED_Y = SystemConf.prorate(15);

	private static final int INIT_LIFE = 1;

	private static final String ACC_STATUS = "accelerate";

	public AccelerateHelper(int x, int y, Container container) {
		super(x, y, WIDTH, HEIGHT, SPEED_X, SPEED_Y, INIT_LIFE, container);
	}

	@Override
	public void makeFunction(Plane plane) {
		if (!(plane instanceof PlayerPlane)) {
			throw new IllegalStateException("只有 PlayerPlane 可以使用 AccelerateHelper");
		}
		PlayerPlane player = (PlayerPlane) plane;
		player.pushStatus(false);
		player.setRatedSpeedX(player.getRatedSpeedX() + SystemConf.frameProrate(5));
		player.setRatedSpeedY(player.getRatedSpeedY() + SystemConf.frameProrate(5));
		player.setImage(getImageLoader().loadImage(PlayerPlane.class, ACC_STATUS));
	}

}
