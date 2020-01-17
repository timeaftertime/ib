package cn.milai.ib.ex;

/**
 * IO 相关异常
 * 2020.01.05
 * @author milai
 */
public class IBIOException extends IBException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IBIOException(Throwable e) {
		super(e);
	}

	public IBIOException(String msg) {
		super(msg);
	}

}
