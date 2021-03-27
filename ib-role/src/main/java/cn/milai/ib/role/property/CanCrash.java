package cn.milai.ib.role.property;

import cn.milai.ib.role.Role;

/**
 * 需要进行碰撞检测的游戏角色
 */
public interface CanCrash extends Role {

	/**
	 * 与另一个游戏角色碰撞时调用
	 * @param crashed
	 */
	void onCrash(CanCrash crashed);

}
