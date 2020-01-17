package cn.milai.ib.character.helper;

import cn.milai.ib.character.Player;
import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.container.Container;

public class AccelerateHelper extends Helper {

	private static final String ACC_STATUS = "accelerate";

	public AccelerateHelper(int x, int y, Container container) {
		super(x, y, container);
	}

	@Override
	public void makeFunction(Player plane) {
		if (!(plane instanceof Player)) {
			throw new IllegalStateException("只有 PlayerPlane 可以使用 AccelerateHelper");
		}
		Player player = (Player) plane;
		player.pushStatus(false);
		player.setRatedSpeedX(player.getRatedSpeedX() + SystemConf.frameProrate(5));
		player.setRatedSpeedY(player.getRatedSpeedY() + SystemConf.frameProrate(5));
		player.setImage(getImageLoader().loadImage(Player.class, ACC_STATUS));
	}

}
