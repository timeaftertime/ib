package cn.milai.ib;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Component;

/**
 * Spring ApplicationContext 持有者
 * @author milai
 * @date 2020.02.20
 */
@Component
public class IBCore implements ApplicationContextAware {

	private static ApplicationContext ctx;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		IBCore.ctx = applicationContext;
	}

	/**
	 * 获取指定类型的实例
	 * @param <T>
	 * @param requiredType
	 * @return
	 * @throws BeansException
	 */
	public static <T> T getBean(Class<T> requiredType) throws BeansException {
		return ctx.getBean(requiredType);
	}

	/**
	 * 以 args 为构造参数获取指定类型的实例
	 * @param <T>
	 * @param requiredType
	 * @param args
	 * @return
	 * @throws BeansException
	 */
	public static <T> T getBean(Class<T> requiredType, Object... args) throws BeansException {
		return ctx.getBean(requiredType, args);
	}

	/**
	 * 获取指定类型的所有实例的 Map ，key 为 bean 的 name
	 * @param <T>
	 * @param requiredType
	 * @return
	 * @throws BeansException
	 */
	public static <T> Map<String, T> getBeansWithName(Class<T> requiredType) throws BeansException {
		return ctx.getBeansOfType(requiredType);
	}

	/**
	 * 获取指定类型的所有实例的 List
	 * @param <T>
	 * @param requiredType
	 * @return
	 * @throws BeansException
	 */
	public static <T> List<T> getBeans(Class<T> requiredType) throws BeansException {
		return new ArrayList<>(ctx.getBeansOfType(requiredType).values());
	}

	/**
	 * 获取指定类型的所有实例的 List ，List 中的元素已经通过 Order 注解排序
	 * @param <T>
	 * @param requiredType
	 * @return
	 * @throws BeansException
	 */
	public static <T> List<T> getBeansOrdered(Class<T> requiredType) throws BeansException {
		List<T> beans = getBeans(requiredType);
		AnnotationAwareOrderComparator.sort(beans);
		return beans;
	}

}
