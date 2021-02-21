package cn.milai.ib.container.plugin;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import cn.milai.ib.container.Container;
import cn.milai.ib.container.ContainerClosedException;
import cn.milai.ib.container.lifecycle.LifecycleContainer;

/**
 * 可添加 {@link ContainerPlugin} 的  {@link Container}
 * @author milai
 * @date 2021.02.09
 */
public interface PluginableContainer extends LifecycleContainer {

	/**
	 * 向容器中添加指定插件
	 * @param plugin
	 * @throws IllegalStateException 若插件正在运行
	 * @throws ContainerClosedException
	 */
	void addPlugin(ContainerPlugin plugin) throws IllegalStateException, ContainerClosedException;

	/**
	 * 从当前容器中移除指定插件，若插件正在运行，将先停止该插件
	 * @param plugin
	 * @throws IllegalStateException 若插件正在运行且不属于当前容器
	 * @throws ContainerClosedException
	 */
	void removePlugin(ContainerPlugin plugin) throws IllegalStateException, ContainerClosedException;

	/**
	 * 获取当前容器中所有指定类型插件列表
	 * @param <T>
	 * @param pluginClass
	 * @return
	 */
	<T extends ContainerPlugin> List<T> getPlugins(Class<T> pluginClass);

	/**
	 * 获取当前容器中指定类型的插件，若有多个该类型插件，随机返回一个，若不存在，返回 null
	 * @param <T>
	 * @param pluginClass
	 * @return
	 */
	default <T extends ContainerPlugin> T getPlugin(Class<T> pluginClass) {
		List<T> ps = getPlugins(pluginClass);
		return ps.isEmpty() ? null : ps.get(0);
	}

	/**
	 * 使用 {@link #getPlugin(Class)} 获取的指定类型 {@link ContainerPlugin} 调用指定 {@link Consumer}
	 * 若 {@link #getPlugin(Class)} 返回 null 将不调用 {@link Consumer} 
	 * @param <T>
	 * @param pluginClass
	 * @param c
	 * @throws ContainerClosedException
	 */
	default <T extends ContainerPlugin> void fire(Class<T> pluginClass, Consumer<T> c) throws ContainerClosedException {
		checkClosed();
		T plugin = getPlugin(pluginClass);
		if (plugin == null) {
			return;
		}
		c.accept(plugin);
	}

	/**
	 * 使用 {@link #getPlugin(Class)} 获取的指定类型 {@link ContainerPlugin} 调用指定 {@link Function} 并返回其返回值
	 * 若 {@link #getPlugin(Class)} 返回 null 将不调用 {@link Function} ，直接返回 {@code def}
	 * @param <T>
	 * @param pluginClass
	 * @param c
	 * @return
	 * @throws
	 */
	default <T extends ContainerPlugin, R> R fire(Class<T> pluginClass, Function<T, R> c, R def)
		throws ContainerClosedException {
		checkClosed();
		T plugin = getPlugin(pluginClass);
		if (plugin == null) {
			return def;
		}
		return c.apply(plugin);
	}

	/**
	 * 以当前容器为目标启动持有的所有插件
	 * @throws ContainerClosedException
	 */
	default void startAllPlugins() throws ContainerClosedException {
		checkClosed();
		for (ContainerPlugin plugin : getPlugins(ContainerPlugin.class)) {
			plugin.start(this);
		}
	}

}
