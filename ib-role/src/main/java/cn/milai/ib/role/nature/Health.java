package cn.milai.ib.role.nature;

import cn.milai.ib.role.Role;

/**
 * 生命值
 * @author milai
 * @date 2021.06.25
 */
public interface Health extends RoleNature {

	/**
	 * 属性名
	 */
	String NAME = "health";

	@Override
	default String name() {
		return NAME;
	}

	/**
	 * 获取初始生命值，即 {@link #init(cn.milai.ib.container.Container)} 时的 {@link #getHP()}
	 * @return
	 */
	int getInitHP();

	/**
	 * 获取角色当前 hp
	 * @return
	 */
	int getHP();

	/**
	 * 设置生命值。只能在 {@link #init(cn.milai.ib.container.Container)} 之前调用
	 * @param hp
	 */
	void setHP(int hp);

	/**
	 * 是否存活
	 * @return
	 */
	boolean isAlive();

	/**
	 * 修改生命值
	 * @param from 引起本次变化的 {@link Role}
	 * @param hp
	 */
	void changeHP(Role from, int hp);

	/**
	 * 获取最后使当前角色生命值减少的游戏角色。若不曾受到伤害，返回 null
	 * @return
	 */
	Role lastAttacker();

}
