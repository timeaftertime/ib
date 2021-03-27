package cn.milai.ib.role.explosion;

import cn.milai.ib.role.Role;

/**
 * 爆炸类游戏对象，不一定是“爆炸”，通称角色死亡后的效果
 * @author milai
 * @date 2020.02.20
 */
public interface Explosion extends Role {

	/**
	 * 属性 lastFrame(爆炸持续帧数) 的 key
	 */
	String P_LAST_FRAME = "lastFrame";

}
