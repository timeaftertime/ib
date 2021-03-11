package cn.milai.ib.container.plugin.character;

import java.util.Arrays;
import java.util.List;

import cn.milai.ib.character.IBCharacter;
import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.listener.ContainerListener;
import cn.milai.ib.container.listener.LifecycleListener;
import cn.milai.ib.container.plugin.BaseMonitorPlugin;

/**
 * {@link AliveCheckPlugin} 默认实现
 * @author milai
 * @date 2021.02.10
 */
public class BaseAliveCheckPlugin extends BaseMonitorPlugin<IBCharacter> implements AliveCheckPlugin {

	public BaseAliveCheckPlugin() {
		super(IBCharacter.class);
	}

	@Override
	protected List<ContainerListener> newListeners() {
		return Arrays.asList(new LifecycleListener() {
			@Override
			public void afterRefresh(LifecycleContainer container) {
				if (container.isPaused()) {
					return;
				}

				long start = System.currentTimeMillis();

				for (IBCharacter character : getAll()) {
					if (!character.isAlive()) {
						character.onDead();
						getContainer().removeObject(character);
					}
				}

				metric(KEY_DELAY, System.currentTimeMillis() - start);
			}
		});
	}

}
