package cn.milai.ib.character.bullet;

import cn.milai.ib.character.property.CanCrash;
import cn.milai.ib.character.property.CanCrashed;
import cn.milai.ib.character.property.HasDamage;
import cn.milai.ib.character.property.Shootable;

/**
 * 子弹类游戏角色
 * @author milai
 * @date 2020.02.20
 */
public interface Bullet extends CanCrash, CanCrashed, HasDamage {

	/**
	 * 属性 power（子弹伤害值） 的 key
	 */
	String P_POWER = "power";

	/**
	 * 获取子弹所属者
	 * @return
	 */
	Shootable getOwner();
}
