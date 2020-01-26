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

	public IBException(String msg, Throwable e) {
		super(msg, e);
	}

	public IBException(Throwable e) {
		super(e);
	}
	
	public IBException(String format, Object...args) {
		this(String.format(format, args));
	}

}
