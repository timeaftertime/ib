package cn.milai.ib.role.property;

import cn.milai.ib.item.BasePainter;
import cn.milai.ib.item.property.Painter;
import cn.milai.ib.role.Role;

/**
 * {@link Role} 的 {@link Painter}
 * @author milai
 * @date 2021.07.14
 */
public class RolePainter extends BasePainter implements RoleProperty {

	@Override
	public Role owner() {
		return (Role) super.owner();
	}

}
