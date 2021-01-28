package cn.milai.ib.character.property;

import cn.milai.ib.character.IBCharacter;
import cn.milai.ib.character.explosion.creator.ExplosionCreator;

/**
 * 可爆炸的游戏角色
 * @author milai
 */
public interface Explosible extends IBCharacter {

	/**
	 * 获取当前角色的爆炸构造器
	 * @return
	 */
	ExplosionCreator getExplosionCreator();
}
