package cn.milai.ib.role.nature.holder;

import cn.milai.ib.role.Role;
import cn.milai.ib.role.nature.Score;

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
	default Score getScore() { return getNature(Score.NAME); }

	/**
	 * 设置关联 {@link Score}
	 * @param s
	 */
	default void setScore(Score s) {
		putNature(s);
	}

}
