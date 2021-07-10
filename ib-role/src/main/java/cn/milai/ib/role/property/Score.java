package cn.milai.ib.role.property;

/**
 * 分数
 * @author milai
 * @date 2021.03.29
 */
public interface Score extends RoleProperty {

	String NAME = "score";

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
