package cn.milai.ib.property;

public interface Alive extends HasScore {

	/**
	 * 是否存活
	 */
	boolean isAlive();
	
	/**
	 * 被 attacker 给予伤害时调用
	 * @param attacker
	 */
	void damagedBy(HasDamage attacker);
	
	/**
	 * 设置为死亡状态
	 */
	void toDead();
	
	/**
	 *  进入死亡状态时应调用此方法
	 */
	void onDead();
	
	/**
	 * 恢复指定生命值
	 * @param life
	 */
	void gainLife(int life);
	
}
