package cn.milai.ib.character.weapn.bullet;

import cn.milai.ib.character.IBCharacter;
import cn.milai.ib.character.property.CanCrash;
import cn.milai.ib.character.property.HasDamage;
import cn.milai.ib.character.property.Rotatable;

/**
 * 子弹类游戏角色
 * @author milai
 * @date 2020.02.20
 */
public interface Bullet extends CanCrash, HasDamage, Rotatable {

	/**
	 * 属性 power（子弹伤害值） 的 key
	 */
	String P_POWER = "power";

	/**
	 * 获取子弹所属者
	 * @return
	 */
	IBCharacter getOwner();
}
