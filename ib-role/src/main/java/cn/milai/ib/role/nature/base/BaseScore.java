package cn.milai.ib.role.nature.base;

import cn.milai.ib.actor.config.Configurable;
import cn.milai.ib.role.Role;
import cn.milai.ib.role.nature.Score;

/**
 * {@link Score} 默认实现
 * @author milai
 * @date 2021.04.01
 */
public class BaseScore extends AbstractRoleNature implements Score {

	private int value;

	public BaseScore(Role owner) {
		super(owner);
	}

	@Configurable
	@Override
	public void setValue(int value) { this.value = value; }

	@Override
	public int getValue() { return value; }

}
