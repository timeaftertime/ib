package cn.milai.ib.client.game.property;

public interface Alive extends HasScore {

	boolean isAlive();
	
	void damagedBy(HasDamage attacker);
	
	/**
	 * 设置为死亡状态
	 */
	void toDead();
	
	/**
	 *  进入死亡状态时应调用此方法
	 */
	void onDead();
	
}
