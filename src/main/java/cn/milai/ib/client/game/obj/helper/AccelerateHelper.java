package cn.milai.ib.client.game.obj.helper;

import cn.milai.ib.client.game.conf.ImageConf;
import cn.milai.ib.client.game.conf.SystemConf;
import cn.milai.ib.client.game.conf.gameprops.SizeConf;
import cn.milai.ib.client.game.conf.gameprops.SpeedConf;
import cn.milai.ib.client.game.form.GameForm;
import cn.milai.ib.client.game.obj.plane.Plane;
import cn.milai.ib.client.game.obj.plane.PlayerPlane;

public class AccelerateHelper extends Helper {

	private static final int INIT_LIFE = 1;

	public AccelerateHelper(int x, int y, GameForm container) {
		super(x, y, SizeConf.ACCELERATE_HELPER_WIDTH, SizeConf.ACCELERATE_HELPER_HEIGHT,
				SpeedConf.ACCELERATE_HELPER_SPEED_X, SpeedConf.ACCELERATE_HELPER_SPEED_Y, INIT_LIFE,
				ImageConf.ACCELERATE_HELPER, container);
	}

	@Override
	public void makeFunction(Plane plane) {
		if (!(plane instanceof PlayerPlane)) {
			throw new IllegalStateException("只有 PlayerPlane 可以使用 AccelerateHelper");
		}
		PlayerPlane player = (PlayerPlane) plane;
		player.pushStatus(false);
		player.setRatedSpeedX(player.getRatedSpeedX() + SystemConf.speedProrate(5));
		player.setRatedSpeedY(player.getRatedSpeedY() + SystemConf.speedProrate(5));
		player.setImage(ImageConf.PLAYER_ACCELERATE);
	}

}
