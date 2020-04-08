package cn.milai.ib.character.property;

import cn.milai.ib.character.IBCharacter;

/**
 * 携带了分数的游戏角色
 * @author milai
 */
public interface HasScore extends IBCharacter {

	String P_SCORE = "score";

	/**
	 * 消灭本对象时获得的分数
	 * @return
	 */
	int getScore();

}
