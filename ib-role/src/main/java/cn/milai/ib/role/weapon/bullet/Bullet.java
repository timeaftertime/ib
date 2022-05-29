package cn.milai.ib.role.weapon.bullet;

import cn.milai.ib.role.Role;
import cn.milai.ib.role.nature.holder.AwareMovableHolder;
import cn.milai.ib.role.nature.holder.ColliderHolder;
import cn.milai.ib.role.nature.holder.DamageHolder;

/**
 * 子弹
 * @author milai
 * @date 2020.02.20
 */
public interface Bullet extends DamageHolder, ColliderHolder, AwareMovableHolder {

	/**
	 * 获取子弹所属者
	 * @return
	 */
	Role getOwner();

}
