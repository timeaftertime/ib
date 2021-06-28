package cn.milai.ib.role.helper;

import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.Movable;
import cn.milai.ib.role.property.base.BaseMovable;

/**
 * {@link Helper} 的 {@link Movable}
 * @author milai
 * @date 2021.06.25
 */
public class HelperMovable extends BaseMovable {

	@Override
	public void afterMove() {
		Role r = owner();
		if (r.getIntY() > r.container().getH()) {
			r.container().removeObject(r);
		}
	}

}
