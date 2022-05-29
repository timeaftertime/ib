package cn.milai.ib.role.nature;

/**
 * 分数
 * @author milai
 * @date 2021.03.29
 */
public interface Score extends RoleNature {

	String NAME = "score";

	@Override
	default String name() {
		return NAME;
	}

	/**
	 * 获取分数
	 * @return
	 */
	int getValue();

	/**
	 * 设置分数
	 * @param value
	 */
	void setValue(int value);

}
