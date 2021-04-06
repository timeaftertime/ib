package cn.milai.ib.container.plugin.role;

import java.util.Arrays;
import java.util.List;

import cn.milai.ib.container.listener.ContainerListener;
import cn.milai.ib.container.listener.Listeners;
import cn.milai.ib.container.plugin.PropertyMonitorPlugin;
import cn.milai.ib.geometry.Point;
import cn.milai.ib.geometry.Rect;
import cn.milai.ib.role.Camp;
import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.Collider;
import cn.milai.ib.role.property.Rotatable;

/**
 * {@link CrashCheckPlugin} 默认实现
 * @author milai
 * @date 2021.02.10
 */
public class BaseCrashCheckPlugin extends PropertyMonitorPlugin<Collider> implements CrashCheckPlugin {

	public BaseCrashCheckPlugin() {
		super(Collider.class);
	}

	@Override
	protected List<ContainerListener> newListeners() {
		return Arrays.asList(Listeners.refreshListener(c -> {
			if (c.isPaused() || c.isPined()) {
				return;
			}
			long start = System.currentTimeMillis();
			List<Collider> colliders = getAll();
			for (int i = 1; i < colliders.size(); i++) {
				for (int j = 0; j < i; j++) {
					Collider c1 = colliders.get(i);
					Collider c2 = colliders.get(j);
					if (isCrashed(c1.getRole(), c2.getRole())) {
						c1.onCrash(c2);
						c2.onCrash(c1);
					}
				}
			}
			metric(KEY_DELAY, System.currentTimeMillis() - start);
		}));
	}

	/**
	 * 判断两个角色是否发生碰撞
	 * @param r1
	 * @param r2
	 * @return
	 */
	public static boolean isCrashed(Role r1, Role r2) {
		if (Camp.sameCamp(r1.getCamp(), r2.getCamp())) {
			return false;
		}
		return new Rect(realBoundPoints(r1)).intersects(new Rect(realBoundPoints(r2)));
	}

	public static Point[] realBoundPoints(Role r) {
		if (r.hasProperty(Rotatable.class)) {
			return r.getProperty(Rotatable.class).getRealBoundPoints();
		}
		return r.getRealBoundPoints();
	}

}
