package cn.milai.ib.role.weapon.bullet;

import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.holder.ColliderHolder;
import cn.milai.ib.role.property.holder.DamageHolder;
import cn.milai.ib.role.property.holder.MovableHolder;
import cn.milai.ib.role.property.holder.RotatableHolder;

/**
 * 子弹类游戏角色
 * @author milai
 * @date 2020.02.20
 */
public interface Bullet extends DamageHolder, ColliderHolder, MovableHolder, RotatableHolder {

	/**
	 * 属性 power（子弹伤害值） 的 key
	 */
	String P_POWER = "power";

	/**
	 * 获取子弹所属者
	 * @return
	 */
	Role getOwner();

}
