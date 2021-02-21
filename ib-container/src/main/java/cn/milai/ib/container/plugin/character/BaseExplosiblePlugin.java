package cn.milai.ib.container.plugin.character;

import java.util.Arrays;
import java.util.List;

import cn.milai.ib.IBObject;
import cn.milai.ib.character.explosion.Explosion;
import cn.milai.ib.character.property.Explosible;
import cn.milai.ib.container.listener.ContainerListener;
import cn.milai.ib.container.listener.ObjectListener;
import cn.milai.ib.container.plugin.BaseMonitorPlugin;

/**
 * {@link ExplosiblePlugin} 默认实现
 * @author milai
 * @date 2021.02.10
 */
public class BaseExplosiblePlugin extends BaseMonitorPlugin<Explosible> implements ExplosiblePlugin {

	public BaseExplosiblePlugin() {
		super(Explosible.class);
	}

	@Override
	protected List<ContainerListener> newListeners() {
		return Arrays.asList(new ObjectListener() {
			@Override
			public void onObjectRemoved(List<IBObject> objs) {
				for (IBObject obj : objs) {
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
			}
		});
	}

}
