package cn.milai.ib.role.property.holder;

import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.Score;

/**
 * {@link Score} 持有者
 * @author milai
 * @date 2021.04.01
 */
public interface ScoreHolder extends Role {

	/**
	 * 获取持有的 {@link Score} 对象
	 * @return
	 */
	default Score getScore() { return getProperty(Score.class); }

	/**
	 * 设置关联 {@link Score}
	 * @param s
	 */
	default void setScore(Score s) {
		putProperty(Score.class, s);
	}

}
