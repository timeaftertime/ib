package cn.milai.ib.role.property;

import cn.milai.ib.config.ItemConfigApplier;
import cn.milai.ib.role.explosion.Explosion;

/**
 * 可爆炸
 * @author milai
 * @date 2021.03.29
 */
public interface Explosible extends RoleProperty, ItemConfigApplier {

	String NAME = "explosible";

	/**
	 * 返回产生的爆炸
	 * @return
	 */
	Explosion[] createExplosions();
}
