package cn.milai.ib.role;

/**
 * CPU 控制的
 * @author milai
 * @date 2020.12.14
 */
public interface Bot {

	/**
	 * 设置攻击目标
	 * @param target
	 */
	void setAttackTarget(PlayerRole target);

	/**
	 * 获取当前的攻击目标，若尚未设置，将返回 null
	 * @return
	 */
	PlayerRole getAttackTarget();

	/**
	 * 重新自动选择一个攻击目标
	 */
	void selectAttackTarget();
}
