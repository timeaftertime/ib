package cn.milai.ib.drama.act.ex;

import cn.milai.ib.drama.act.Act;

/**
 * Act 的操作数错误异常，通常是 .drama 文件格式错误或版本错误
 * 2020.01.12
 * @author milai
 */
public class IllegalOperandsException extends ActExecuteException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IllegalOperandsException(Act act, String msg) {
		super(act, msg);
	}

}
