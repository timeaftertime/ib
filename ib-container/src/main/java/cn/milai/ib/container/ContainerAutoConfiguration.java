package cn.milai.ib.container;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.milai.ib.container.conf.MetricsPluginConf;
import cn.milai.ib.container.plugin.ContainerPlugin;
import cn.milai.ib.container.plugin.control.BaseControlPlugin;
import cn.milai.ib.container.plugin.control.ControlPlugin;
import cn.milai.ib.container.plugin.metrics.BaseMetricsPlugin;
import cn.milai.ib.container.plugin.metrics.MetricsPlugin;
import cn.milai.ib.container.plugin.physics.BasePhysicsPlugin;
import cn.milai.ib.container.plugin.physics.PhysicsPlugin;
import cn.milai.ib.container.plugin.printer.BasePrinterPlugin;
import cn.milai.ib.container.plugin.printer.PrinterPlugin;
import cn.milai.ib.container.plugin.role.AliveCheckPlugin;
import cn.milai.ib.container.plugin.role.BaseAliveCheckPlugin;
import cn.milai.ib.container.plugin.role.BaseExplosiblePlugin;
import cn.milai.ib.container.plugin.role.ExplosiblePlugin;

/**
 * {@link ContainerPlugin} 默认配置
 * @author milai
 * @date 2021.05.15
 */
@Configuration
public class ContainerAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(Container.class)
	public BaseDramaContainerFactory baseContainer(List<ContainerPlugin> plugins) {
		return new BaseDramaContainerFactory(plugins);
	}

	@Bean
	@ConditionalOnMissingBean(ControlPlugin.class)
	public ControlPlugin baseControlPlugin() {
		return new BaseControlPlugin();
	}

	@Bean
	@ConditionalOnMissingBean(PhysicsPlugin.class)
	public PhysicsPlugin basePhysicsPlugin() {
		return new BasePhysicsPlugin();
	}

	@Bean
	@ConditionalOnMissingBean(AliveCheckPlugin.class)
	public AliveCheckPlugin baseAliveCheckPlugin() {
		return new BaseAliveCheckPlugin();
	}

	@Bean
	@ConditionalOnMissingBean(ExplosiblePlugin.class)
	public ExplosiblePlugin baseExplosiblePlugin() {
		return new BaseExplosiblePlugin();
	}

	@Bean
	@ConditionalOnMissingBean(PrinterPlugin.class)
	public PrinterPlugin basePrinterPlugin() {
		return new BasePrinterPlugin();
	}

	@Bean
	@ConditionalOnBean(MetricsPluginConf.class)
	@ConditionalOnMissingBean(MetricsPlugin.class)
	public MetricsPlugin baseMetricsPlugin(MetricsPluginConf conf) {
		return new BaseMetricsPlugin(conf);
	}

}
