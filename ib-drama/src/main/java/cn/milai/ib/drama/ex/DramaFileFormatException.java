package cn.milai.ib.drama.ex;

import cn.milai.ib.ex.IBException;

/**
 * .drama 文件格式错误异常
 * 2020.01.01
 * @author milai
 */
public class DramaFileFormatException extends IBException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DramaFileFormatException() {
		
	}
	
	public DramaFileFormatException(String msg) {
		super(msg);
	}

}
