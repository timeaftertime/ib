package cn.milai.ib.container.plugin.metrics;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 数据指标
 * @author milai
 * @date 2021.02.26
 */
public class Metrics {

	private Map<String, String> kvs = Maps.newConcurrentMap();

	/**
	 * 设置指定 {@code k} 的值为 {@code v}
	 * @param k
	 * @param v
	 * @return
	 */
	public Metrics put(String k, String v) {
		kvs.put(k, v);
		return this;
	}

	/**
	 * 清空所有已经设置的指标
	 */
	public void clear() {
		kvs.clear();
	}

	/**
	 * 当前指标是否为空
	 * @return
	 */
	public boolean isEmpty() { return kvs.isEmpty(); }

	@Override
	public String toString() {
		List<String> pairs = Lists.newArrayList();
		for (String k : kvs.keySet()) {
			pairs.add(String.format("%s=%s", k, kvs.get(k)));
		}
		return String.join(", ", pairs);
	}
}
