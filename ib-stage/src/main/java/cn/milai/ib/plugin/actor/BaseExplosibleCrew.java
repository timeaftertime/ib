package cn.milai.ib.plugin.actor;

import java.util.function.Consumer;

import cn.milai.ib.actor.Actor;
import cn.milai.ib.plugin.SharableCrew;
import cn.milai.ib.role.Role;
import cn.milai.ib.role.explosion.Explosion;
import cn.milai.ib.role.nature.Explosible;
import cn.milai.ib.stage.event.RemoveActorEvent;

/**
 * {@link ExplosibleCrew} 默认实现
 * @author milai
 * @date 2021.02.10
 */
public class BaseExplosibleCrew extends SharableCrew implements ExplosibleCrew {

	@Override
	public Consumer<RemoveActorEvent> createOnRemoveActor() {
		return e -> {
			for (Actor i : e.actors()) {
				if (!(i instanceof Role)) {
					continue;
				}
				Role role = (Role) i;
				if (role.getHealth().isAlive() || !role.hasNature(Explosible.NAME)) {
					continue;
				}
				Explosible explosible = role.getNature(Explosible.NAME);
				for (Explosion explosion : explosible.createExplosions()) {
					e.stage().addActor(explosion);
				}
			}
		};
	}

}
