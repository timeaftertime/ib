package cn.milai.ib.property;

import cn.milai.ib.character.ExplosionCreator;

/**
 * 可爆炸的
 *
 * @author milai
 */
public interface Explosible {

	/**
	 * 获取爆炸类
	 * @return
	 */
	Class<? extends ExplosionCreator> getExplosionCreator();
}
