package cn.milai.ib.container.plugin.metrics;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.milai.common.base.Strings;
import cn.milai.common.thread.counter.Counter;
import cn.milai.common.thread.counter.DownCounter;
import cn.milai.ib.IBCore;
import cn.milai.ib.container.conf.MetricsPluginConf;
import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.lifecycle.LifecycleListener;
import cn.milai.ib.container.listener.ContainerListener;
import cn.milai.ib.container.plugin.ContainerPlugin;
import cn.milai.ib.container.plugin.ListenersPlugin;
import cn.milai.ib.container.pluginable.PluginListener;
import cn.milai.ib.container.pluginable.PluginableContainer;

/**
 * {@link MetricsPlugin} 默认实现
 * @author milai
 * @date 2021.02.25
 */
public class BaseMetricPlugin extends ListenersPlugin implements MetricsPlugin {

	private static final String HEADER = "=============>";

	private static final String FOOTER = "<===============================================================";

	private static final Logger LOG = LoggerFactory.getLogger(BaseMetricPlugin.class);

	private MetricsPluginConf conf = IBCore.getBean(MetricsPluginConf.class);

	private Counter counter = conf.getInterval() > 0 ? new DownCounter(conf.getInterval()) : new Counter() {};

	/**
	 * 每种 category 对应的 {@link MetricsPlugin} 的指标信息。
	 * 其中 List 的下标与 {@link #categoryReigistered} 对应
	 */
	private Map<String, List<Metrics>> metricsByCategory = Maps.newConcurrentMap();

	/**
	 * 每种 category 对应的 {@link MetricsPlugin} 列表
	 */
	private Map<String, List<MetrizablePlugin>> categoryReigistered = Maps.newConcurrentMap();

	@Override
	public void metric(MetrizablePlugin plugin, String k, Object v) {
		String category = plugin.getCategory();
		if (!conf.getCategories().getOrDefault(category, false)) {
			return;
		}
		List<Metrics> metricsOfCategory = getMetricsByCategory(category);
		List<MetrizablePlugin> plugins = getPluginsByCategory(category);
		int index = plugins.indexOf(plugin);
		if (index < 0) {
			synchronized (plugins) {
				if (!plugins.contains(plugin)) {
					plugins.add(plugin);
					index = plugins.size() - 1;
					metricsOfCategory.add(new Metrics());
				} else {
					index = plugins.indexOf(plugin);
				}
			}
		}
		metricsOfCategory.get(index).put(k, v + "");
	}

	@Override
	public List<ContainerListener> newListeners() {
		return Arrays.asList(
			new LifecycleListener() {
				@Override
				public void afterRefresh(LifecycleContainer container) {
					long start = System.currentTimeMillis();

					counter.count();
					if (counter.isMet()) {
						counter.reset();
						logMetrics();

						metric(KEY_DELAY, System.currentTimeMillis() - start);
					}
				}
			},
			new PluginListener() {
				@Override
				public void onPluginRemoved(PluginableContainer container, ContainerPlugin plugin) {
					if (plugin instanceof MetrizablePlugin) {
						unregister((MetrizablePlugin) plugin);
					}
				}
			}
		);
	}

	private List<MetrizablePlugin> getPluginsByCategory(String category) {
		return categoryReigistered.computeIfAbsent(category, c -> Lists.newArrayList());
	}

	/**
	 * 线程安全地获取指定 {@code category} 的 {@link Metrics} 列表
	 * @param category
	 * @return
	 */
	private List<Metrics> getMetricsByCategory(String category) {
		synchronized (metricsByCategory) {
			return metricsByCategory.computeIfAbsent(category, c -> Lists.newArrayList());
		}
	}

	private void logMetrics() {
		List<String> lines = Lists.newArrayList();
		lines.add(HEADER);
		synchronized (metricsByCategory) {
			String printFormat = "%-" + maxNameLen() + "s|%s";
			for (String category : metricsByCategory.keySet()) {
				List<Metrics> metricsList = Lists.newArrayList(metricsByCategory.get(category));
				for (int i = 0; i < metricsList.size(); i++) {
					Metrics metrics = metricsList.get(i);
					if (metrics.isEmpty()) {
						continue;
					}
					String name = category + (metricsList.size() > 1 ? suffix(i + 1) : "");
					lines.add(String.format(printFormat, name, metrics));
					metrics.clear();
				}
			}
		}
		lines.add(FOOTER);
		LOG.info(Strings.joinLineSeparator(lines));
	}

	private int maxNameLen() {
		int maxLen = 0;
		for (String category : metricsByCategory.keySet()) {
			int len = category.length();
			int size = metricsByCategory.get(category).size();
			if (size > 1) {
				len += suffix(size).length();
			}
			maxLen = Math.max(maxLen, len);
		}
		return maxLen;
	}

	private String suffix(int index) {
		return "(" + index + ")";
	}

	@Override
	public void unregister(MetrizablePlugin plugin) {
		String category = plugin.getCategory();
		List<MetrizablePlugin> plugins = getPluginsByCategory(category);
		synchronized (plugins) {
			int index = plugins.indexOf(plugin);
			if (index > 0) {
				plugins.remove(index);
				getMetricsByCategory(category).remove(index);
			}
		}
	}

}
