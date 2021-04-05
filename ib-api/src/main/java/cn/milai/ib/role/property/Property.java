package cn.milai.ib.role.property;

import cn.milai.ib.role.Role;

/**
 * {@link Role} 的属性
 * @author milai
 * @date 2021.03.28
 */
public interface Property {

	/**
	 * 获取当前 {@link Property} 关联的 {@link Role}
	 * @return
	 */
	Role getRole();
}
