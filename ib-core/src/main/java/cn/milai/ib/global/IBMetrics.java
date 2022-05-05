package cn.milai.ib.global;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;

/**
 * {@link MeterRegistry} 配置
 * @author milai
 * @date 2022.05.04
 */
public class IBMetrics {

	private static final MeterRegistry METER_REGISTRY = Metrics.globalRegistry;

	/**
	 * 获取全局 Metrics 注册中心
	 * @return
	 */
	public static MeterRegistry registry() {
		return METER_REGISTRY;
	}

}
