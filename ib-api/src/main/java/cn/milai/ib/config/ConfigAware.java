package cn.milai.ib.config;

/**
 * 可重定义 {@link Configurable} 所使用的 {@link Config} code 的类
 * @author milai
 * @date 2021.06.10
 */
public interface ConfigAware {

	/**
	 * 获取关联的 {@link Config} code
	 * @return
	 */
	default String getConfigCode() { return getClass().getName(); }

}
