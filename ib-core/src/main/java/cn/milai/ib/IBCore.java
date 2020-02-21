package cn.milai.ib;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import cn.milai.ib.ex.IBException;
import cn.milai.ib.util.IOUtil;

/**
 * Spring ApplicationContext 持有者
 * @author milai
 * @date 2020.02.20
 */
public class IBCore {

	private static final Logger log = LoggerFactory.getLogger(IBCore.class);

	public static final String FACTORIES_RESOURCE_LOCATION = "META-INF/ib.factories";

	private static ApplicationContext ctx;

	static {
		List<String> basePackages = Lists.newArrayList();
		try {
			Enumeration<URL> urls = IBCore.class.getClassLoader().getResources(FACTORIES_RESOURCE_LOCATION);
			while (urls.hasMoreElements()) {
				basePackages.addAll(IOUtil.toListFilter(urls.nextElement(), line -> !line.startsWith("#")));
			}
		} catch (IOException e) {
			log.error("读取 factories 文件失败", e);
			throw new IBException("读取 factories 文件失败", e);
		}
		ctx = new AnnotationConfigApplicationContext(basePackages.toArray(new String[0]));
	}

	/**
	 * 主动触发容器初始化
	 */
	public static void init() {
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

}
