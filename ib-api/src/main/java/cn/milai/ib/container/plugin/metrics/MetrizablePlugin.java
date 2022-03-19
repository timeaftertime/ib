package cn.milai.ib.container.plugin.metrics;

import cn.milai.ib.container.plugin.ContainerPlugin;

/**
 * 可使用 {@link Metrics} 汇报指标信息的 {@link ContainerPlugin}
 * @author milai
 * @date 2021.02.24
 */
public interface MetrizablePlugin extends ContainerPlugin {

	/**
	 * 默认 {@link ContainerPlugin} 类别
	 */
	public static final String CATEGORY_DEF = "default";

	/**
	 * 插件工作一次的时间
	 */
	public static final String KEY_DELAY = "delay";

	/**
	 * 获取插件所属类别
	 * @return
	 */
	default String getCategory() { return CATEGORY_DEF; }

	/**
	 * 汇报当前 {@link MetrizablePlugin} 的 {@code k} 指标为 {@code v}
	 * @param k
	 * @param v
	 */
	default void metric(String k, Object v) {
		containers().forEach(c -> c.fire(MetricsPlugin.class, p -> p.metric(this, k, v)));
	}

}
