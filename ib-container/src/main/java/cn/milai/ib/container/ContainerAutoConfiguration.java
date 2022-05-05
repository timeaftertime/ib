package cn.milai.ib.container;

import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import cn.milai.ib.container.listener.ContainerListeners;
import cn.milai.ib.container.plugin.ContainerPlugin;
import cn.milai.ib.container.plugin.control.BaseControlPlugin;
import cn.milai.ib.container.plugin.control.ControlPlugin;
import cn.milai.ib.container.plugin.physics.BasePhysicsPlugin;
import cn.milai.ib.container.plugin.physics.PhysicsPlugin;
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
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@ConditionalOnMissingBean(Container.class)
	public Stage stage(List<ContainerPlugin> plugins) {
		Stage stage = new BaseStage();
		for (ContainerPlugin plugin : plugins) {
			stage.addPlugin(plugin);
		}
		stage.addLifecycleListener(ContainerListeners.closeListener(c -> plugins.forEach(p -> p.destroy())));
		return stage;
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@ConditionalOnMissingBean(ControlPlugin.class)
	public ControlPlugin baseControlPlugin() {
		return new BaseControlPlugin();
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@ConditionalOnMissingBean(PhysicsPlugin.class)
	public PhysicsPlugin basePhysicsPlugin() {
		return new BasePhysicsPlugin();
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@ConditionalOnMissingBean(AliveCheckPlugin.class)
	public AliveCheckPlugin baseAliveCheckPlugin() {
		return new BaseAliveCheckPlugin();
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@ConditionalOnMissingBean(ExplosiblePlugin.class)
	public ExplosiblePlugin baseExplosiblePlugin() {
		return new BaseExplosiblePlugin();
	}

}
