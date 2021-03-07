package cn.milai.ib.container.conf;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import cn.milai.ib.container.plugin.metrics.MetricsPlugin;

/**
 * {@link MetricsPlugin} 相关配置
 * @author milai
 * @date 2021.02.26
 */
@Component
@ConfigurationProperties(prefix = "ib.container.plugin.metrics")
public class MetricsPluginConf {

	private int interval = 0;
	private Map<String, Boolean> categories = new HashMap<>();

	public int getInterval() { return interval; }

	public void setInterval(int interval) { this.interval = interval; }

	public Map<String, Boolean> getCategories() { return categories; }

	public void setCategories(Map<String, Boolean> categories) { this.categories = categories; }

}
