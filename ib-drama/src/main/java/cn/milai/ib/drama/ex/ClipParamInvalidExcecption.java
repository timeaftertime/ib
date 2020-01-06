package cn.milai.ib.drama.ex;

import cn.milai.ib.ex.IBException;

/**
 * 剧本片段参数不能为 null 的异常
 *
 * 2019.12.14
 *
 * @author milai
 */
public class ClipParamInvalidExcecption extends IBException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClipParamInvalidExcecption(String key, String value) {
		super(String.format("剧本参数不合法, %s == %s", key, value));
	}

}
