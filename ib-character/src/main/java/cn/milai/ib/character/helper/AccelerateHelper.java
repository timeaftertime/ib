package cn.milai.ib.character.helper;

import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.container.Container;
import cn.milai.ib.loader.ImageLoader;
import cn.milai.ib.obj.Player;

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
		player.setRatedSpeedX(player.getRatedSpeedX() + SystemConf.frameProrate(2));
		player.setRatedSpeedY(player.getRatedSpeedY() + SystemConf.frameProrate(2));
		player.setImage(ImageLoader.load(plane.getClass(), ACC_STATUS));
	}

}
