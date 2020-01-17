package cn.milai.ib.character.explosion.creator;

import cn.milai.ib.IBObject;
import cn.milai.ib.character.Explosion;
import cn.milai.ib.character.ExplosionCreator;
import cn.milai.ib.character.explosion.DefaultExplosion;
import cn.milai.ib.property.Explosible;

/**
 * 产生单个 {@codeDefaultExplosion } 的爆炸产生器
 * 2019.11.29
 * @author milai
 */
public class DefaultExplosionCreator implements ExplosionCreator {

	/**
	 * 爆炸产生物
	 */
	private IBObject owner;

	/**
	 * 构造一个与 explosible 关联的爆炸产生器
	 * explosible 必须是 {@code GameObject } 的子类
	 * @param explosible
	 */
	public DefaultExplosionCreator(Explosible explosible) {
		if (!(explosible instanceof IBObject)) {
			throw new IllegalArgumentException(
					String.format("参数 %s 不是 %s 的子类", explosible.getClass().getName(),
							IBObject.class.getName()));
		}
		this.owner = (IBObject) explosible;
	}

	@Override
	public Explosion[] createExplosions() {
		return new Explosion[] { new DefaultExplosion((int) owner.getCenterX(), (int) owner.getCenterY(), owner.getContainer()) };
	}

}
