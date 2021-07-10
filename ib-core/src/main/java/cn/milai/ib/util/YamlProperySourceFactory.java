package cn.milai.ib.util;

import java.io.IOException;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

/**
 * Yaml {@link PropertySource} 工厂类
 * @author milai
 * @date 2021.07.07
 */
public class YamlProperySourceFactory implements PropertySourceFactory {

	@Override
	public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
		YamlPropertiesFactoryBean bean = new YamlPropertiesFactoryBean();
		bean.setResources(resource.getResource());
		return new PropertiesPropertySource(name, bean.getObject());
	}

}
