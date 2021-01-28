package cn.milai.ib.mode;

import cn.milai.ib.ex.IBException;

/**
 * 无法解析剧本 Code 的异常
 * @author milai
 * @date 2020.03.05
 */
public class DramaCanNotResolveException extends IBException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DramaCanNotResolveException(String dramaCode) {
		super(String.format("剧本无法解析，dramaCode = %s", dramaCode));
	}

}
