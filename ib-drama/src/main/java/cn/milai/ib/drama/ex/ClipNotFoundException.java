package cn.milai.ib.drama.ex;

import cn.milai.ib.ex.IBException;

/**
 * 剧本片段没有找到的异常
 *
 * 2019.12.14
 *
 * @author milai
 */
public class ClipNotFoundException extends IBException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClipNotFoundException(String clipId) {
		super(String.format("没有找到指定剧本片段类： %s", clipId));
	}

}
