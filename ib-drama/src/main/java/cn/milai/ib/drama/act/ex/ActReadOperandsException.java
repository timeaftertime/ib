package cn.milai.ib.drama.act.ex;

import cn.milai.ib.drama.act.Act;

/**
 * 读取 Act 操作数时发生的异常
 *
 * 2019.12.20
 *
 * @author milai
 */
public class ActReadOperandsException extends ActException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActReadOperandsException(Act act, Throwable e) {
		super(String.format("读取操作数发生异常， act = %s", act.getCode()), e);
	}

}
