package cn.milai.ib.role.property;

import cn.milai.ib.role.Role;
import cn.milai.ib.role.explosion.creator.ExplosionCreator;

/**
 * 可爆炸的游戏角色
 * @author milai
 */
public interface Explosible extends Role {

	/**
	 * 获取当前角色的爆炸构造器
	 * @return
	 */
	ExplosionCreator getExplosionCreator();
}
