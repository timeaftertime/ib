package cn.milai.ib.character.property;

import cn.milai.ib.character.IBCharacter;

/**
 * 需要进行碰撞检测的游戏角色
 */
public interface CanCrash extends IBCharacter {

	/**
	 * 与另一个游戏角色碰撞时调用
	 * @param crashed
	 */
	void onCrash(CanCrash crashed);

}
