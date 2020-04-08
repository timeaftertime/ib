package cn.milai.ib.loader.ex;

import cn.milai.ib.ex.IBException;

/**
 * 剧本字符串没有找到的异常
 * @author milai
 * @date 2020.03.23
 */
public class DramaStringNotFoundException extends IBException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DramaStringNotFoundException(String dramaCode, String stringCode) {
		super(String.format("剧本字符串不存在：dramaCode = %s, stringCode = %s", dramaCode, stringCode));
	}

}
