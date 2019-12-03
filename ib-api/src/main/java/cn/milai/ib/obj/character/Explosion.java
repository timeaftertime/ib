package cn.milai.ib.obj.character;

import cn.milai.ib.container.Container;
import cn.milai.ib.property.HasDamage;

/**
 * 爆炸的抽象基类
 *
 * 2019.11.29
 *
 * @author milai
 */
public abstract class Explosion extends IBCharacter {

	/**
	 * 持续的帧数
	 */
	private static final String P_LAST_FRAME = "lastFrame";

	private final long LAST_FRAMES = longProp(P_LAST_FRAME);

	private long endFrame;

	public Explosion(int x, int y, Container container) {
		super(x, y, container);
		this.endFrame = getContainer().currentFrame() + LAST_FRAMES;
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
	public void damagedBy(HasDamage attacker) {
	}

	@Override
	public int getScore() {
		return 0;
	}

}
