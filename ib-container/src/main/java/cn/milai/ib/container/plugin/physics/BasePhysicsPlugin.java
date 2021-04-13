package cn.milai.ib.container.plugin.physics;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;

import cn.milai.common.base.Collects;
import cn.milai.ib.container.listener.ContainerListener;
import cn.milai.ib.container.listener.Listeners;
import cn.milai.ib.container.listener.PropertyMonitor;
import cn.milai.ib.container.plugin.ListenersPlugin;
import cn.milai.ib.role.Camp;
import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.Collider;
import cn.milai.ib.role.property.Movable;
import cn.milai.ib.role.property.Rotatable;

/**
 * {@link PhysicsPlugin} 默认实现
 * @author milai
 * @date 2021.04.07
 */
public class BasePhysicsPlugin extends ListenersPlugin implements PhysicsPlugin {

	private PropertyMonitor<Collider> colliders;
	private PropertyMonitor<Movable> movables;

	@Override
	protected void afterAddListeners() {
		colliders = PropertyMonitor.monitor(getContainer(), Collider.class);
		movables = PropertyMonitor.monitor(getContainer(), Movable.class);
	}

	@Override
	protected List<ContainerListener> newListeners() {
		return Arrays.asList(Listeners.refreshListener(c -> {
			if (c.isPaused() || c.isPined()) {
				return;
			}
			long start = System.currentTimeMillis();
			Queue<Mover> movers = new PriorityBlockingQueue<Mover>(
				Collects.mapList(movables.getProps(), Mover::new)
			);
			Set<Mover> started = new HashSet<>();
			while (!movers.isEmpty()) {
				Mover now = movers.poll();
				Movable m = now.getMovable();
				if (started.add(now)) {
					m.beforeMove();
				}
				if (doMove(now, movers.isEmpty() ? 0 : movers.peek().getFuelRatio())) {
					m.afterMove();
					continue;
				}
				movers.offer(now);
			}
			metric(KEY_DELAY, System.currentTimeMillis() - start);
		}));
	}

	/**
	 * 移动指定 {@link Mover} ，返回是否移动完毕
	 * @param now
	 * @param nextFuelRatio 下一个待移动 {@link Mover} 的 fuelRatio
	 * @return
	 */
	private boolean doMove(Mover now, double nextFuelRatio) {
		Movable m = now.getMovable();
		Role r = m.getRole();
		if (!r.hasProperty(Collider.class)) {
			r.setX(r.getX() + m.getSpeedX());
			r.setY(r.getY() + m.getSpeedY());
			return true;
		}
		return moveUntil(nextFuelRatio, now);
	}

	/**
	 *  移动 {@link Mover} 直到其 {@link Mover#getFuelRatio()} 小于指定值或等于 0
	 * @param nextFuelRatio
	 * @param now
	 * @return
	 */
	private boolean moveUntil(double nextFuelRatio, Mover now) {
		Role r1 = now.getMovable().getRole();
		Collider c1 = r1.getProperty(Collider.class);
		while (now.getFuelRatio() >= nextFuelRatio && now.getFuelRatio() != 0) {
			now.move();
			now.getMovable().onMove();

			for (Role r2 : colliders.getAll()) {
				if (!r2.isAlive()) {
					continue;
				}
				if (isCollided(r1, r2)) {
					Collider c2 = r2.getProperty(Collider.class);
					c1.onCollided(c2);
					c2.onCollided(c1);
					if (!r1.isAlive()) {
						return true;
					}
				}
			}
		}
		return now.getFuelRatio() == 0;
	}

	/**
	 * 判断两个角色是否发生碰撞
	 * @param r1
	 * @param r2
	 * @return
	 */
	public static boolean isCollided(Role r1, Role r2) {
		if (Camp.sameCamp(r1.getCamp(), r2.getCamp())) {
			return false;
		}
		return Rotatable.getBoundRect(r1).intersects(Rotatable.getBoundRect(r2));
	}
}
