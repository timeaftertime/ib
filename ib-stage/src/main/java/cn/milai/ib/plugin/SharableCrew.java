package cn.milai.ib.plugin;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 可关联多个 {@link Pluginable} 的 {@link Crew}
 * @author milai
 * @date 2022.03.18
 */
public class SharableCrew extends AbstractCrew {

	private Set<Pluginable<?>> stages = Collections.newSetFromMap(new ConcurrentHashMap<>());

	@Override
	public final boolean doPlug(Pluginable<?> c) {
		return stages.add(c);
	}

	@Override
	public boolean unplug(Pluginable<?> c) {
		stages.remove(c);
		return true;
	}

	@Override
	public Set<Pluginable<?>> pluginables() {
		return Collections.unmodifiableSet(stages);
	}

}
