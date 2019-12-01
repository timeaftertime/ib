package cn.milai.ib.ex;

/**
 * 系统所有异常类的基类
 *
 * 2019.11.24
 *
 * @author milai
 */
public class IBException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IBException() {

	}

	public IBException(String message) {
		super(message);
	}

}
