package cn.milai.ib.ex;

/**
 * 加载配置信息失败异常
 * 2020.01.10
 * @author milai
 */
public class LoadConfigException extends IBIOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoadConfigException(Throwable e) {
		super(e);
	}

	public LoadConfigException(String resource) {
		super(String.format("配置文件 %s 不存在", resource));
	}

}
