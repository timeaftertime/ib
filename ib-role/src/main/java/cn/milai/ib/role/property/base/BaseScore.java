package cn.milai.ib.role.property.base;

import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.Score;

/**
 * {@link Score} 默认实现
 * @author milai
 * @date 2021.04.01
 */
public class BaseScore extends BaseProperty implements Score {

	private int value;

	public BaseScore(Role role, int value) {
		super(role);
		this.value = value;
	}

	@Override
	public int getValue() { return value; }

}
