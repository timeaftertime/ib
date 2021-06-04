package cn.milai.ib.container;

import java.util.List;

import org.springframework.beans.factory.FactoryBean;

import cn.milai.ib.container.plugin.ContainerPlugin;

/**
 * 容器工厂类的默认实现
 * @author milai
 * @date 2021.05.22
 */
public class BaseDramaContainerFactory implements FactoryBean<DramaContainer> {

	private List<ContainerPlugin> plugins;

	public BaseDramaContainerFactory(List<ContainerPlugin> plugins) {
		this.plugins = plugins;
	}

	@Override
	public DramaContainer getObject() throws Exception {
		DramaContainer container = new BaseDramaContainer();
		for (ContainerPlugin plugin : plugins) {
			container.addPlugin(plugin);
		}
		container.startAllPlugins();
		return container;
	}

	@Override
	public Class<?> getObjectType() { return DramaContainer.class; }

}
