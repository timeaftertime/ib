package cn.milai.ib.role.property.base;

import cn.milai.ib.config.Configurable;
import cn.milai.ib.role.property.Score;

/**
 * {@link Score} 默认实现
 * @author milai
 * @date 2021.04.01
 */
public class BaseScore extends BaseRoleProperty implements Score {

	private int value;

	@Configurable
	@Override
	public void setValue(int value) { this.value = value; }

	@Override
	public int getValue() { return value; }

}
