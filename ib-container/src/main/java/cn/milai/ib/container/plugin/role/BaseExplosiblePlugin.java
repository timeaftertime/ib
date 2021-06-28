package cn.milai.ib.container.plugin.role;

import java.util.Arrays;
import java.util.List;

import cn.milai.ib.container.listener.ContainerListener;
import cn.milai.ib.container.listener.ContainerListeners;
import cn.milai.ib.container.plugin.ListenersPlugin;
import cn.milai.ib.role.Role;
import cn.milai.ib.role.explosion.Explosion;
import cn.milai.ib.role.property.Explosible;

/**
 * {@link ExplosiblePlugin} 默认实现
 * @author milai
 * @date 2021.02.10
 */
public class BaseExplosiblePlugin extends ListenersPlugin implements ExplosiblePlugin {

	@Override
	protected List<ContainerListener> newListeners() {
		return Arrays.asList(ContainerListeners.roleListener(null, (c, roles) -> {
			for (Role role : roles) {
				if (role.getHealth().isAlive() || !role.hasProperty(Explosible.class)) {
					continue;
				}
				for (Explosion explosion : role.getProperty(Explosible.class).createExplosions()) {
					getContainer().addObject(explosion);
				}
			}
		}));
	}

}
