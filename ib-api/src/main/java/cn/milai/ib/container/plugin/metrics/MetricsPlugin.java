package cn.milai.ib.container.plugin.metrics;

import cn.milai.ib.container.plugin.ContainerPlugin;

/**
 * 收集和打印 {@link MetrizablePlugin} 指标信息的 {@link ContainerPlugin}
 * @author milai
 * @date 2021.02.25
 */
public interface MetricsPlugin extends MetrizablePlugin {

	/**
	 * 记录指定 {@link MetrizablePlugin} 的 {@code k} 指标信息为 {@code v}。
	 * @param plugin
	 * @param k
	 * @param v
	 */
	void metric(MetrizablePlugin plugin, String k, Object v);

	/**
	 * 删除指定 {@link MetrizablePlugin} 的所有指标信息
	 * @param plugin
	 */
	void unregister(MetrizablePlugin plugin);

	@Override
	default String getCategory() { return "metrics"; }
}
