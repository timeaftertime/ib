package cn.milai.ib.role.explosion;

import cn.milai.ib.role.Role;
import cn.milai.ib.role.nature.Health;
import cn.milai.ib.role.nature.base.BaseHealth;
import cn.milai.ib.stage.Stage;

/**
 * {@link Explosion} 的 {@link Health} 实现
 * @author milai
 * @date 2021.06.25
 */
public class ExplosionHealth extends BaseHealth {

	private long endFrame = Long.MAX_VALUE;

	public ExplosionHealth(Role owner) {
		super(owner);
	}

	public long getEndFrame() { return endFrame; }

	public void setEndFrame(long endFrame) { this.endFrame = endFrame; }

	@Override
	public boolean isAlive() {
		Stage stage = owner().stage();
		return stage == null ? false : stage.lifecycle().getFrame() <= endFrame;
	}

}
