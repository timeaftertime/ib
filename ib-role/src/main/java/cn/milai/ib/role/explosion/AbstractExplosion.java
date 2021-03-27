package cn.milai.ib.role.explosion;

import cn.milai.ib.Paintable;
import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.role.AbstractRole;

/**
 * 爆炸的抽象基类
 * 2019.11.29
 * @author milai
 */
public abstract class AbstractExplosion extends AbstractRole implements Explosion {

	private final long LAST_FRAMES = longProp(P_LAST_FRAME);

	private long endFrame;

	public AbstractExplosion(double x, double y, LifecycleContainer container) {
		super(x, y, container);
		this.endFrame = getContainer().getFrame() + LAST_FRAMES;
	}

	@Override
	public boolean isAlive() { return getContainer().getFrame() <= endFrame; }

	@Override
	public void toDead() {
		endFrame = getContainer().getFrame();
	}

	@Override
	public int getZ() { return Paintable.DEFAULT_Z + 1; }

}
