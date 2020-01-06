package cn.milai.ib.drama.statics.constant;

import cn.milai.ib.ex.IBException;

/**
 * 未知常量异常
 * 2019.12.31
 * @author milai
 */
public class UnknownConstantException extends IBException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnknownConstantException(int code) {
		super(String.format("未知常量 code = %d", code));
	}

}
