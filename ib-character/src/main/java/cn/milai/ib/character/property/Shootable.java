package cn.milai.ib.character.property;

import cn.milai.ib.constant.BulletType;
import cn.milai.ib.obj.IBCharacter;

/**
 * 可发射子弹的游戏角色
 * @author milai
 */
public interface Shootable extends IBCharacter {

	/**
	 * 发射指定类型的子弹，返回是否成功发射
	 * @param type
	 */
	boolean shoot(BulletType type);

	/**
	 * 发射 MAIN 类型子弹，返回是否成功发射
	 * @return
	 */
	default boolean shoot() {
		return shoot(BulletType.MAIN);
	}

}
