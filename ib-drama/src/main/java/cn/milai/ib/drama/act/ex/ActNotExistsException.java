package cn.milai.ib.drama.act.ex;

/**
 * code 所对应的 Act 不存在异常
 *
 * 2019.12.14
 *
 * @author milai
 */
public class ActNotExistsException extends ActException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActNotExistsException(int code) {
		super(String.format("字节码 0x%s 所对应的 Act 不存在", Integer.toString(code, 16)));
	}

}
