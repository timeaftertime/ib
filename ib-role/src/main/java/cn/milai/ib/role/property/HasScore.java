package cn.milai.ib.role.property;

import cn.milai.ib.role.Role;

/**
 * 携带了分数的游戏角色
 * @author milai
 */
public interface HasScore extends Role {

	String P_SCORE = "score";

	/**
	 * 消灭本对象时获得的分数
	 * @return
	 */
	int getScore();

}
