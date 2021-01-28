package cn.milai.ib.loader.ex;

import cn.milai.ib.ex.IBException;

/**
 * 找不到指定配置的异常
 *
 * 2019.11.24
 *
 * @author milai
 */
public class PropNotFoundException extends IBException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PropNotFoundException(Class<?> clazz, String key) {
		super(String.format("找不到 %s 的属性 %s 的配置", clazz.getName(), key));
	}

}
