package cn.milai.ib.obj.character.explosion.creator;

import cn.milai.ib.obj.IBObject;
import cn.milai.ib.obj.character.Explosion;
import cn.milai.ib.obj.character.ExplosionCreator;
import cn.milai.ib.obj.character.explosion.DefaultExplosion;
import cn.milai.ib.property.Explosible;

/**
 * 产生单个 {@codeDefaultExplosion } 的爆炸产生器
 *
 * 2019.11.29
 *
 * @author milai
 */
public class DefaultExplosionCreator implements ExplosionCreator {

	private IBObject owner;

	/**
	 * 构造一个与 explosible 关联的爆炸产生器
	 * explosible 必须是 {@code GameObject } 的子类
	 * 
	 * @param explosible
	 */
	public DefaultExplosionCreator(Explosible explosible) {
		if (!(explosible instanceof IBObject)) {
			throw new IllegalArgumentException(
					String.format("参数 %s 不是 %s 的子类", explosible.getClass().getName(), IBObject.class.getName()));
		}
		this.owner = (IBObject) explosible;
	}

	@Override
	public Explosion[] createExplosions() {
		return new Explosion[] { new DefaultExplosion(owner.getX(), owner.getY(), owner.getContainer()) };
	}

}
