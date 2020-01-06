package cn.milai.ib.drama.ex;

import cn.milai.ib.ex.IBException;

public class ClipReadException extends IBException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ClipReadException(String clipURL) {
		super(String.format("读取剧本资源 %s 失败", clipURL));
	}

}
