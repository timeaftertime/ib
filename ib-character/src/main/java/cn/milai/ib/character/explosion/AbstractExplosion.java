package cn.milai.ib.character.explosion;

import cn.milai.ib.Paintable;
import cn.milai.ib.character.AbstractIBCharacter;
import cn.milai.ib.container.ui.UIContainer;

/**
 * 爆炸的抽象基类
 * 2019.11.29
 * @author milai
 */
public abstract class AbstractExplosion extends AbstractIBCharacter implements Explosion {

	private final long LAST_FRAMES = longProp(P_LAST_FRAME);

	private long endFrame;

	public AbstractExplosion(int x, int y, UIContainer container) {
		super(x, y, container);
		this.endFrame = getContainer().getFrame() + LAST_FRAMES;
	}

	@Override
	public boolean isAlive() {
		return getContainer().getFrame() <= endFrame;
	}

	@Override
	public void toDead() {
		endFrame = getContainer().getFrame();
	}

	@Override
	public int getZ() {
		return Paintable.DEFAULT_Z + 1;
	}

}
