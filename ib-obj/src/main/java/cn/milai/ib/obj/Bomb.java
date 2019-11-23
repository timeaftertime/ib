package cn.milai.ib.obj;

import cn.milai.ib.AudioConf;
import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.container.Container;
import cn.milai.ib.property.HasDamage;
import cn.milai.ib.property.Paintable;

public class Bomb extends GameEntity {

	private static final int WIDTH = SystemConf.prorate(48);
	private static final int HEIGHT = SystemConf.prorate(48);

	private static final long LAST_FRAMES = 10;

	long endFrame;

	public Bomb(int x, int y, Container container) {
		super(x, y, WIDTH, HEIGHT, 0, container);
		this.endFrame = getContainer().currentFrame() + LAST_FRAMES;
		AudioConf.BOMB.play();
	}

	@Override
	public boolean isAlive() {
		return getContainer().currentFrame() <= endFrame;
	}

	@Override
	public void toDead() {
		endFrame = getContainer().currentFrame();
	}

	@Override
	public void onDead() {
	}

	@Override
	public void damagedBy(HasDamage attacker) {
	}

	@Override
	public int getScore() {
		return 0;
	}

	@Override
	public int getPaintLayer() {
		return Paintable.BOMB_LAYER;
	}

}
