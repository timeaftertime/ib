package cn.milai.ib.character.property;

import cn.milai.ib.obj.IBCharacter;

/**
 * 一个表明本游戏角色可以撞击 @see CanCrashed 对象的接口
 */
public interface CanCrash extends IBCharacter {

	/**
	 * 与 CanCrashed 碰撞后，若 !sameCamp(canCrashed) 则调用
	 * @param crashed
	 */
	void onCrash(CanCrashed crashed);

}
