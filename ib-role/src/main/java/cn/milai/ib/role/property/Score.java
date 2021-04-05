package cn.milai.ib.role.property;

/**
 * 分数
 * @author milai
 * @date 2021.03.29
 */
public interface Score extends Property {

	String P_SCORE = "score";

	/**
	 * 消灭本对象时获得的分数
	 * @return
	 */
	int getValue();

}
