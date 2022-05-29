package cn.milai.ib.role.nature;

import cn.milai.ib.actor.nature.Nature;
import cn.milai.ib.role.Role;

/**
 * {@link Role} 的 {@link Nature}
 * @author milai
 * @date 2021.06.25
 */
public interface RoleNature extends Nature {

	@Override
	Role owner();

}
