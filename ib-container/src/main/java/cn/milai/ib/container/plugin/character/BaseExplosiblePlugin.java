package cn.milai.ib.container.plugin.character;

import cn.milai.ib.IBObject;
import cn.milai.ib.character.explosion.Explosion;
import cn.milai.ib.character.property.Explosible;
import cn.milai.ib.container.plugin.BaseObjectPlugin;
import cn.milai.ib.container.plugin.ObjectLifecycleListener;

/**
 * {@link ExplosiblePlugin} 默认实现
 * @author milai
 * @date 2021.02.10
 */
public class BaseExplosiblePlugin extends BaseObjectPlugin<Explosible> implements ExplosiblePlugin {

	public BaseExplosiblePlugin() {
		super(Explosible.class);
	}

	@Override
	protected ObjectLifecycleListener newEventListener() {
		return new ObjectLifecycleListener() {
			@Override
			public void onObjectRemoved(IBObject obj) {
				if (!(obj instanceof Explosible)) {
					return;
				}
				Explosible explosible = (Explosible) obj;
				if (explosible.isAlive()) {
					return;
				}
				for (Explosion explosion : explosible.getExplosionCreator().createExplosions(explosible)) {
					getContainer().addObject(explosion);
				}
			}
		};
	}
}
