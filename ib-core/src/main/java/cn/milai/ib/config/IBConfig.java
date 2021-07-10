package cn.milai.ib.config;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;

import cn.milai.common.base.Chars;
import cn.milai.ib.item.Item;
import cn.milai.ib.item.property.Property;

/**
 * {@link Item} 配置管理类
 * @author milai
 * @date 2021.07.07
 */
@Configuration
public class IBConfig implements EnvironmentAware {

	public static final String CONFIG_PREFIX = "ibconfig";

	public static final String GLOBAL_PREFIX = "global";

	private static final Logger LOG = LoggerFactory.getLogger(IBConfig.class);

	private static Environment env;

	@Override
	public void setEnvironment(Environment environment) { env = environment; }

	/**
	 * 设置配置到指定对象 {@code o}，返回原对象
	 * @param <T>
	 * @param o
	 * @return
	 */
	public static <T> T apply(T o) {
		String configCode = o.getClass().getName();
		if (o instanceof ConfigAware) {
			configCode = ((ConfigAware) o).getConfigCode();
		}
		apply(configCode, o);
		if (o instanceof Item) {
			Map<String, Property> properties = ((Item) o).properties();
			for (String name : properties.keySet()) {
				apply(configCode + "." + name, properties.get(name));
			}
		}
		return o;
	}

	private static <T> T apply(String configCode, T o) {
		applyFields(configCode, o);
		applyMethods(configCode, o);
		return o;
	}

	private static void applyFields(String configCode, Object o) {
		Class<?> clazz = o.getClass();
		while (clazz != Object.class) {
			for (Field field : clazz.getDeclaredFields()) {
				Configurable config = AnnotationUtils.getAnnotation(field, Configurable.class);
				if (config == null) {
					continue;
				}
				String property = propertyKey(configCode, getConfigValue(config, field.getName()));
				Object value = env.getProperty(property, field.getType());
				if (value == null) {
					LOG.debug("获取配置失败: property = {}", property);
					continue;
				}
				field.setAccessible(true);
				try {
					field.set(o, value);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					LOG.debug(
						"设置字段失败: property = {}, field = {}, e = {}",
						property, field.getName(), ExceptionUtils.getStackTrace(e)
					);
				}
			}
			clazz = clazz.getSuperclass();
		}
	}

	private static void applyMethods(String configCode, Object o) {
		Class<?> clazz = o.getClass();
		for (Method method : clazz.getMethods()) {
			Parameter[] params = method.getParameters();
			if (params.length != 1) {
				continue;
			}
			Configurable config = AnnotationUtils.getAnnotation(method, Configurable.class);
			if (config == null) {
				continue;
			}
			String property = propertyKey(configCode, getConfigValue(config, parseSetterProperty(method.getName())));
			Object value = env.getProperty(property, params[0].getType());
			try {
				method.invoke(o, value);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				LOG.debug(
					"调用 setter 失败: class = {}, method = {}, property = {}, value = {}, e = {}",
					clazz, method.getName(), property, value, ExceptionUtils.getStackTrace(e)
				);
			}
		}
	}

	private static String parseSetterProperty(String methodName) {
		if (!methodName.startsWith("set")) {
			return null;
		}
		char[] chs = methodName.substring(3).toCharArray();
		for (int i = 0; i < chs.length && Chars.isUpper(chs[i]); i++) {
			chs[i] = Chars.toLower(chs[i]);
		}
		return new String(chs);
	}

	private static String propertyKey(String configCode, String name) {
		return CONFIG_PREFIX + "." + GLOBAL_PREFIX + "." + configCode + "." + name;
	}

	private static String getConfigValue(Configurable configuable, String defaultValue) {
		if (configuable == null || configuable.value().equals("")) {
			return defaultValue;
		}
		return configuable.value();
	}

}
