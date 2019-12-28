package cn.milai.ib.drama.act.ex;

import cn.milai.ib.drama.act.Act;

/**
 * 动作执行时发生的异常
 *
 * 2019.12.19
 *
 * @author milai
 */
public class ActExecuteException extends ActException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActExecuteException(Act act, Throwable e) {
		super(String.format("执行 Act 时发生异常, act = %s", act.getCode()), e);
	}

	public ActExecuteException(int pc, Throwable e) {
		super(String.format("执行 Act 时发生异常, pc = %d", pc), e);
	}

}
