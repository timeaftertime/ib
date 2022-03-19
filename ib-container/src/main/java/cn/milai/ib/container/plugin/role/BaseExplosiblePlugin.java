package cn.milai.ib.container.plugin.role;

import java.util.List;

import cn.milai.ib.container.Container;
import cn.milai.ib.container.plugin.SharableContainerPlugin;
import cn.milai.ib.item.Item;
import cn.milai.ib.role.Role;
import cn.milai.ib.role.explosion.Explosion;
import cn.milai.ib.role.property.Explosible;

/**
 * {@link ExplosiblePlugin} 默认实现
 * @author milai
 * @date 2021.02.10
 */
public class BaseExplosiblePlugin extends SharableContainerPlugin implements ExplosiblePlugin {

	@Override
	public void onRemoved(Container container, List<Item> items) {
		for (Item i : items) {
			if (!(i instanceof Role)) {
				continue;
			}
			Role role = (Role) i;
			if (role.getHealth().isAlive() || !role.hasProperty(Explosible.class)) {
				continue;
			}
			for (Explosion explosion : role.getProperty(Explosible.class).createExplosions()) {
				container.addObject(explosion);
			}
		}
	}

}
