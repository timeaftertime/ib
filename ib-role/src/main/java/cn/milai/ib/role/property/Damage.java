package cn.milai.ib.role.property;

/**
 * 伤害值
 * @author milai
 * @date 2021.03.29
 */
public interface Damage extends RoleProperty {

	String NAME = "damage";

	/**
	 * 获取当前对象伤害值
	 * @return
	 */
	int getValue();

	/**
	 * 设置当前对象伤害值
	 * @param value
	 */
	void setValue(int value);

}
