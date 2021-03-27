package cn.milai.ib.role.property;

import cn.milai.ib.role.Role;

/**
 * 有伤害值的游戏角色
 * @author milai
 * @date 2020.02.20
 */
public interface HasDamage extends Role {

	/**
	 * 获取当前对象伤害值
	 * @return
	 */
	int getDamage();

}
