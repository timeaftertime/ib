package cn.milai.ib.character.property;

import cn.milai.ib.obj.IBCharacter;

/**
 * 有伤害值的游戏角色
 * @author milai
 * @date 2020.02.20
 */
public interface HasDamage extends IBCharacter {

	/**
	 * 获取当前对象
	 * @return
	 */
	int getDamage();

}
