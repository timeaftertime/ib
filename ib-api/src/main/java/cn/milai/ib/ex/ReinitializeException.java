package cn.milai.ib.ex;

/**
 * 重复初始化的异常
 * @author milai
 * @date 2021.06.25
 */
public class ReinitializeException extends IBException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReinitializeException(Object o) {
		super("重复初始化:" + o.getClass().getName());
	}

}
