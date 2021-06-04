package cn.milai.ib.container;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.milai.ib.container.conf.MetricsPluginConf;
import cn.milai.ib.container.plugin.ContainerPlugin;
import cn.milai.ib.container.plugin.control.BaseControlPlugin;
import cn.milai.ib.container.plugin.control.ControlPlugin;
import cn.milai.ib.container.plugin.media.BaseMediaPlugin;
import cn.milai.ib.container.plugin.media.MediaPlugin;
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
import cn.milai.ib.container.plugin.ui.UIPlugin;
import cn.milai.ib.container.plugin.ui.form.BaseFormUIPlugin;
import cn.milai.ib.container.plugin.ui.form.FormUIPlugin;
import cn.milai.ib.container.plugin.ui.form.KeyMapping;
import cn.milai.ib.container.plugin.ui.form.MouseMapping;

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
	@ConditionalOnMissingBean(MediaPlugin.class)
	public MediaPlugin baseMediaPlugin() {
		return new BaseMediaPlugin();
	}

	@Bean
	@ConditionalOnMissingBean(PrinterPlugin.class)
	public PrinterPlugin basePrinterPlugin() {
		return new BasePrinterPlugin();
	}

	@Bean
	@ConditionalOnMissingBean(UIPlugin.class)
	@Autowired(required = false)
	public FormUIPlugin baseUIPlugin(KeyMapping keyMapping, MouseMapping mouseMapping) {
		BaseFormUIPlugin formUIPlugin = new BaseFormUIPlugin();
		if (keyMapping != null) {
			formUIPlugin.setKeyMapping(keyMapping);
		}
		if (mouseMapping != null) {
			formUIPlugin.setMouseMapping(mouseMapping);
		}
		return formUIPlugin;
	}

	@Bean
	@ConditionalOnBean(MetricsPluginConf.class)
	@ConditionalOnMissingBean(MetricsPlugin.class)
	public MetricsPlugin baseMetricsPlugin(MetricsPluginConf conf) {
		return new BaseMetricsPlugin(conf);
	}

}
