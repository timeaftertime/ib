package cn.milai.ib.role.nature;

/**
 * 伤害值
 * @author milai
 * @date 2021.03.29
 */
public interface Damage extends RoleNature {

	String NAME = "damage";

	@Override
	default String name() {
		return NAME;
	}

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
