package cn.milai.ib.obj.character.helper;

import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.container.Container;
import cn.milai.ib.obj.character.plane.Plane;
import cn.milai.ib.obj.character.plane.PlayerPlane;

public class AccelerateHelper extends Helper {

	private static final String ACC_STATUS = "accelerate";

	public AccelerateHelper(int x, int y, Container container) {
		super(x, y, container);
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
