package cn.milai.ib.role.property;

import cn.milai.ib.item.property.Property;
import cn.milai.ib.role.Role;

/**
 * {@link Role} 的 {@link Property}
 * @author milai
 * @date 2021.06.25
 */
public interface RoleProperty extends Property {

	@Override
	Role owner();

}
