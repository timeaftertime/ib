package cn.milai.ib.config;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import cn.milai.common.base.Classes;
import cn.milai.ib.obj.IBObject;
import cn.milai.ib.obj.property.Property;

/**
 * {@link Configurable} 处理器
 * @author milai
 * @date 2021.06.10
 */
@Component
public class ConfigProcessor implements BeanPostProcessor {

	private static final Logger LOG = LoggerFactory.getLogger(ConfigProcessor.class);

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		Configurable configurable = AnnotationUtils.findAnnotation(bean.getClass(), Configurable.class);
		if (configurable == null) {
			return bean;
		}
		String configCode = bean.getClass().getName();
		if (bean instanceof ConfigAware) {
			configCode = ((ConfigAware) bean).getConfigCode();
		}
		Config config = Configs.load(configCode, () -> buildDefaultConfig(bean));
		applyConfig(config, bean);
		return bean;
	}

	private Config buildDefaultConfig(Object bean) {
		Config config = new Config(bean.getClass().getName(), parsePropertyDescriptors(bean));
		if (bean instanceof IBObject) {
			IBObject o = (IBObject) bean;
			Map<String, Property> properties = o.properties();
			for (String name : properties.keySet()) {
				config.setChild(name, parsePropertyDescriptors(properties.get(name)));
			}
		}
		return config;
	}

	private Map<String, String> parsePropertyDescriptors(Object bean) {
		Map<String, String> kvs = new HashMap<>();
		for (Field field : bean.getClass().getDeclaredFields()) {
			PropertyDescriptor pd = getParamPropertyDescriptor(bean.getClass(), field.getName());
			String name = field.getName();
			Method readMethod = pd.getReadMethod();
			if (readMethod == null) {
				continue;
			}
			try {
				kvs.put(name, readMethod.invoke(bean).toString());
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				LOG.debug("调用 readMethod 失败: name = {}, e = {}", name, ExceptionUtils.getStackTrace(e));
				continue;
			}
		}
		return kvs;
	}

	private void applyConfig(Config config, Object bean) {
		for (String name : config.names()) {
			applyValue(bean, name, config.get(name));
		}
		if (bean instanceof IBObject) {
			IBObject o = (IBObject) bean;
			Map<String, Property> properties = o.properties();
			for (String name : properties.keySet()) {
				applyConfig(config.getChild(name), properties.get(name));
			}
		}
	}

	private void applyValue(Object bean, String name, String value) {
		PropertyDescriptor pd = getParamPropertyDescriptor(bean.getClass(), name);
		Method writeMethod = pd.getWriteMethod();
		try {
			writeMethod.invoke(bean, Classes.toSingle(value, writeMethod.getParameterTypes()[0]));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			LOG.warn("调用 writeMethod 失败: name = {}, e = {}", name, ExceptionUtils.getStackTrace(e));
		}
	}

	/**
	 * 获取 clazz 的 name  {@link PropertyDescriptor}。
	 * 若字段没有只接受一个简单类型参数的 setter 方法，返回 null.
	 * @param clazz
	 * @param name
	 * @return
	 */
	private PropertyDescriptor getParamPropertyDescriptor(Class<?> clazz, String name) {
		PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(clazz, name);
		if (pd == null) {
			LOG.debug("获取描述符失败: class = {}, name = {}", clazz.getName(), name);
			return null;
		}
		Method writeMethod = pd.getWriteMethod();
		if (writeMethod == null) {
			LOG.debug("获取 setter 失败: class = {}, name = {}", clazz.getName(), name);
			return null;
		}
		Class<?>[] parameterTypes = writeMethod.getParameterTypes();
		if (parameterTypes.length != 1 || !Classes.isSingle(parameterTypes[0])) {
			LOG.debug("忽略非法 setter: name = {}, params = {}", name, Arrays.toString(parameterTypes));
			return null;
		}
		return pd;
	}

}