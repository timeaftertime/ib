package cn.milai.ib.container.plugin.character;

import cn.milai.ib.character.IBCharacter;
import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.plugin.BaseObjectPlugin;
import cn.milai.ib.container.plugin.ObjectLifecycleListener;

/**
 * {@link AliveCheckPlugin} 默认实现
 * @author milai
 * @date 2021.02.10
 */
public class BaseAliveCheckPlugin extends BaseObjectPlugin<IBCharacter> implements AliveCheckPlugin {

	public BaseAliveCheckPlugin() {
		super(IBCharacter.class);
	}

	@Override
	protected ObjectLifecycleListener newEventListener() {
		return new ObjectLifecycleListener() {
			@Override
			public void afterRefresh(LifecycleContainer container) {
				if (container.isPaused()) {
					return;
				}
				for (IBCharacter character : getAll()) {
					if (!character.isAlive()) {
						character.onDead();
						getContainer().removeObject(character);
					}
				}
			}
		};
	}
}
