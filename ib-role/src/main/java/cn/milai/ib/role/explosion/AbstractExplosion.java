package cn.milai.ib.role.explosion;

import cn.milai.ib.role.BaseRole;
import cn.milai.ib.role.property.Health;

/**
 * 爆炸的抽象基类
 * 2019.11.29
 * @author milai
 */
public abstract class AbstractExplosion extends BaseRole implements Explosion {

	@Override
	protected Health createHealth() {
		return new ExplosionHealth();
	}

	@Override
	public int getZ() { return super.getZ() + 1; }
}
