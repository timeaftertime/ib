package cn.milai.ib.role.explosion;

import cn.milai.ib.actor.config.Configurable;
import cn.milai.ib.role.BaseRole;
import cn.milai.ib.role.nature.Health;
import cn.milai.ib.stage.Stage;

/**
 * 爆炸的抽象基类
 * 2019.11.29
 * @author milai
 */
public abstract class AbstractExplosion extends BaseRole implements Explosion {

	private long lastFrame;

	@Override
	protected Health createHealth() {
		return new ExplosionHealth(this);
	}

	@Override
	protected void onEnterStage(Stage stage) {
		ExplosionHealth health = (ExplosionHealth) getHealth();
		health.setEndFrame(stage.lifecycle().getFrame() + lastFrame);
	}

	public long getLastFrame() { return lastFrame; }

	@Configurable
	public void setLastFrame(long lastFrame) { this.lastFrame = lastFrame; }

	@Override
	public int getZ() { return super.getZ() + 1; }
}
