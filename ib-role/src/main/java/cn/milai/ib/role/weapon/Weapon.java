package cn.milai.ib.role.weapon;

import cn.milai.ib.role.Role;

/**
 * 武器
 * @author milai
 * @date 2020.04.01
 */
public interface Weapon {

	/**
	 * owner 使用当前武器发动一次攻击
	 */
	void attack();

	/**
	 * 获取当前武器持有者
	 * 没有持有者时返回 null
	 * @return
	 */
	Role getOwner();

	/**
	 * 设置武器持有者
	 * @param owner
	 */
	void setOwner(Role owner);
}
