package cn.milai.ib.plugin;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import cn.milai.beginning.collection.Creator;
import cn.milai.ib.stage.Stage;

/**
 * {@link ExclusiveCrew} 默认实现
 * @author milai
 * @date 2022.03.19
 */
public class BaseExclusiveCrew extends AbstractCrew implements ExclusiveCrew {

	private AtomicReference<Stage> stage = new AtomicReference<>();

	@Override
	public final boolean doPlug(Pluginable<?> c) {
		if (!Stage.class.isInstance(c)) {
			throw new IllegalArgumentException("不支持的类型:" + c.getClass());
		}
		Stage s = (Stage) c;
		if (stage.compareAndSet(s, s)) {
			return true;
		}
		if (stage.compareAndSet(null, s)) {
			onPlug(s);
			return true;
		}
		return false;
	}

	/**
	 * 绑定插件时调用
	 * @param s
	 */
	protected void onPlug(Stage s) {
	}

	@Override
	public final boolean unplug(Pluginable<?> c) {
		if (!Stage.class.isInstance(c)) {
			throw new IllegalArgumentException("不支持的类型:" + c.getClass());
		}
		Stage s = (Stage) c;
		if (stage.compareAndSet(s, null)) {
			onUnplug(s);
		}
		return true;
	}

	/**
	 * 取消绑定插件时调用
	 * @param s
	 */
	protected void onUnplug(Stage s) {
	}

	@Override
	public Set<Pluginable<?>> pluginables() {
		Pluginable<?> c = stage.get();
		return c == null ? Collections.emptySet() : Creator.asSet(c);
	}

	@Override
	public Stage stage() {
		return stage.get();
	}

}
