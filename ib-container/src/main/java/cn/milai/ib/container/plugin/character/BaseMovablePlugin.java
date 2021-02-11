package cn.milai.ib.container.plugin.character;

import cn.milai.ib.character.property.Movable;
import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.plugin.BaseObjectPlugin;
import cn.milai.ib.container.plugin.ObjectLifecycleListener;

/**
 * {@link MovablePlugin} 默认实现
 * @author milai
 * @date 2021.02.10
 */
public class BaseMovablePlugin extends BaseObjectPlugin<Movable> implements MovablePlugin {

	public BaseMovablePlugin() {
		super(Movable.class);
	}

	@Override
	protected ObjectLifecycleListener newEventListener() {
		return new ObjectLifecycleListener() {
			@Override
			public void afterRefresh(LifecycleContainer container) {
				if (container.isPaused() || container.isPined()) {
					return;
				}
				for (Movable m : getAll()) {
					m.move();
				}
			}
		};
	}

}
