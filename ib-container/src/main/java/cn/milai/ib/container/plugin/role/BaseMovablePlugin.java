package cn.milai.ib.container.plugin.role;

import java.util.Arrays;
import java.util.List;

import cn.milai.ib.container.listener.ContainerListener;
import cn.milai.ib.container.listener.Listeners;
import cn.milai.ib.container.plugin.PropertyMonitorPlugin;
import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.Movable;

/**
 * {@link MovablePlugin} 默认实现
 * @author milai
 * @date 2021.02.10
 */
public class BaseMovablePlugin extends PropertyMonitorPlugin<Movable> implements MovablePlugin {

	public BaseMovablePlugin() {
		super(Movable.class);
	}

	@Override
	protected List<ContainerListener> newListeners() {
		return Arrays.asList(Listeners.refreshListener(c -> {
			if (c.isPaused() || c.isPined()) {
				return;
			}
			long start = System.currentTimeMillis();
			for (Movable m : getAll()) {
				Role r = m.getRole();
				m.beforeMove();
				r.setX(r.getX() + m.getSpeedX());
				r.setY(r.getY() + m.getSpeedY());
				m.onMove();
				m.afterMove();
			}
			metric(KEY_DELAY, System.currentTimeMillis() - start);
		}));
	}

}
