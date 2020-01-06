package cn.milai.ib.drama.act.ex;

import cn.milai.ib.ex.IBException;

/**
 * 动作相关异常
 * 2019.12.21
 * @author milai
 */
public class ActException extends IBException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActException() {
	}

	public ActException(String msg) {
		super(msg);
	}

	public ActException(String msg, Throwable e) {
		super(msg, e);
	}

}
