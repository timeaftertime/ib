package cn.milai.ib.role.explosion.creator;

import cn.milai.ib.role.explosion.Explosion;
import cn.milai.ib.role.property.Explosible;

/**
 * 爆炸产生器
 * 2019.11.29
 * @author milai
 */
public interface ExplosionCreator {

	/**
	 * 返回产生的爆炸
	 * @return
	 */
	Explosion[] createExplosions(Explosible owner);

}
