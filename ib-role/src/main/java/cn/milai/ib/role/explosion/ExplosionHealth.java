package cn.milai.ib.role.explosion;

import cn.milai.ib.config.Configurable;
import cn.milai.ib.role.property.Health;
import cn.milai.ib.role.property.base.BaseHealth;

/**
 * {@link Explosion} 的 {@link Health} 实现
 * @author milai
 * @date 2021.06.25
 */
public class ExplosionHealth extends BaseHealth {

	private long endFrame = -1;

	private long lastFrame;

	public long getLastFrame() { return lastFrame; }

	@Configurable
	public void setLastFrame(long lastFrame) { this.lastFrame = lastFrame; }

	@Override
	protected void initRoleProperty() {
		endFrame = owner().container().getFrame() + lastFrame;
	}

	@Override
	public boolean isAlive() { return owner().container().getFrame() <= endFrame; }

}
