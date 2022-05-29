package cn.milai.ib.plugin;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 可插件的
 * @author milai
 * @date 2022.05.15
 */
public interface Pluginable<T extends Plugin> {

	/**
	 * 获取已经注册的所有指定类型 {@link Plugin}
	 * @param <C>
	 * @param pluginClass
	 * @return
	 */
	<C extends T> Set<C> getPlugins(Class<C> pluginClass);

	/**
	 * 获取当前容器中指定类型的插件，若有多个该类型插件，随机返回一个，若不存在，返回 null
	 * @param <C>
	 * @param pluginClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	default <C extends T> C getPlugin(Class<C> pluginClass) {
		for (Plugin plugin : getPlugins(pluginClass)) {
			if (pluginClass.isInstance(plugin)) {
				return (C) plugin;
			}
		}
		return null;
	}

	/**
	 * 使用 {@link #getPlugin(Class)} 获取的指定类型 {@link Plugin} 调用指定 {@link Consumer}
	 * 若 {@link #getPlugin(Class)} 返回 null 将不调用 {@link Consumer} 
	 * @param <C>
	 * @param pluginClass
	 * @param c
	 */
	default <C extends T> void fire(Class<C> pluginClass, Consumer<C> c) {
		C plugin = getPlugin(pluginClass);
		if (plugin == null) {
			return;
		}
		c.accept(plugin);
	}

	/**
	 * 使用 {@link #getPlugin(Class)} 获取的指定类型 {@link Plugin} 调用指定 {@link Function} 并返回其返回值
	 * 若 {@link #getPlugin(Class)} 返回 null 将不调用 {@link Function} ，直接返回 {@code def}
	 * @param <T>
	 * @param <R>
	 * @param pluginClass
	 * @param c
	 * @return
	 */
	default <C extends T, R> R fire(Class<C> pluginClass, Function<C, R> c, R def) {
		C plugin = getPlugin(pluginClass);
		if (plugin == null) {
			return def;
		}
		return c.apply(plugin);
	}

}
