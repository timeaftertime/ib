package cn.milai.ib.character.explosion.creator;

import cn.milai.ib.character.explosion.DefaultExplosion;
import cn.milai.ib.character.explosion.Explosion;
import cn.milai.ib.character.property.Explosible;

/**
 * 产生单个 {@codeDefaultExplosion } 的爆炸产生器
 * 2019.11.29
 * @author milai
 */
public class DefaultExplosionCreator implements ExplosionCreator {

	@Override
	public Explosion[] createExplosions(Explosible owner) {
		return new Explosion[] {
			new DefaultExplosion((int) owner.getCenterX(), (int) owner.getCenterY(), owner.getContainer()),
		};
	}

}
