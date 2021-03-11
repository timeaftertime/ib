package cn.milai.ib.container.plugin.character;

import java.util.Arrays;
import java.util.List;

import cn.milai.ib.character.Camp;
import cn.milai.ib.character.IBCharacter;
import cn.milai.ib.character.property.CanCrash;
import cn.milai.ib.character.property.Rotatable;
import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.listener.ContainerListener;
import cn.milai.ib.container.listener.LifecycleListener;
import cn.milai.ib.container.plugin.BaseMonitorPlugin;
import cn.milai.ib.geometry.Rect;

/**
 * {@link CrashCheckPlugin} 默认实现
 * @author milai
 * @date 2021.02.10
 */
public class BaseCrashCheckPlugin extends BaseMonitorPlugin<CanCrash> implements CrashCheckPlugin {

	public BaseCrashCheckPlugin() {
		super(CanCrash.class);
	}

	@Override
	protected List<ContainerListener> newListeners() {
		return Arrays.asList(new LifecycleListener() {
			@Override
			public void afterRefresh(LifecycleContainer container) {
				if (container.isPaused() || container.isPined()) {
					return;
				}

				long start = System.currentTimeMillis();

				List<CanCrash> canCrashs = getAll();
				for (int i = 1; i < canCrashs.size(); i++) {
					for (int j = 0; j < i; j++) {
						CanCrash canCrash1 = canCrashs.get(i);
						CanCrash canCrash2 = canCrashs.get(j);
						if (isCrashed(canCrash1, canCrash2)) {
							canCrash1.onCrash(canCrash2);
							canCrash2.onCrash(canCrash1);
						}
					}
				}

				metric(KEY_DELAY, System.currentTimeMillis() - start);
			}
		});
	}

	/**
	 * 判断两个角色是否发生碰撞
	 * @param c1
	 * @param c2
	 * @return
	 */
	public static boolean isCrashed(IBCharacter c1, IBCharacter c2) {
		if (Camp.sameCamp(c1.getCamp(), c2.getCamp())) {
			return false;
		}
		if ((c1 instanceof Rotatable) || (c2 instanceof Rotatable)) {
			return new Rect(c1.getRealBoundPoints()).intersects(new Rect(c2.getRealBoundPoints()));
		}
		return c1.getIntX() + c1.getIntW() >= c2.getIntX()
			&& c1.getIntX() <= c2.getIntX() + c2.getIntW()
			&& c1.getIntY() + c1.getIntH() >= c2.getIntY()
			&& c1.getIntY() <= c2.getIntY() + c2.getIntH();
	}

}
