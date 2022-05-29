package cn.milai.ib.actor.config;

/**
 * 提供配置注入方法的接口
 * @author milai
 * @date 2021.07.09
 */
public interface ConfigApplier<T> {

	/**
	 * 注入属性到指定对象并返回原对象
	 * @param <O>
	 * @param o
	 * @return
	 */
	default <O extends T> O apply(O o) {
		return IBConfig.apply(o);
	}
}