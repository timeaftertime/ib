package cn.milai.ib.ex;

/**
 * 配置文件未找到异常
 * 2020.01.10
 * @author milai
 */
public class ConfigNotFoundException extends IBIOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConfigNotFoundException(Class<?> clazz) {
		super(String.format("配置文件不存在： character = %s", clazz.getName()));
	}

}
