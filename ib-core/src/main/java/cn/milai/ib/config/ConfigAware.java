package cn.milai.ib.config;

/**
 * 感知到 {@link Configurable} 的类
 * @author milai
 * @date 2021.07.07
 */
public interface ConfigAware {

	/**
	 * 获取配置 code
	 * @return
	 */
	default String getConfigCode() { return getClass().getName(); }

}
