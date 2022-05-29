package cn.milai.ib.role.nature;

import cn.milai.ib.actor.config.ItemConfigApplier;
import cn.milai.ib.role.explosion.Explosion;

/**
 * 可爆炸
 * @author milai
 * @date 2021.03.29
 */
public interface Explosible extends RoleNature, ItemConfigApplier {

	String NAME = "explosible";

	@Override
	default String name() {
		return NAME;
	}

	/**
	 * 返回产生的爆炸
	 * @return
	 */
	Explosion[] createExplosions();
}
