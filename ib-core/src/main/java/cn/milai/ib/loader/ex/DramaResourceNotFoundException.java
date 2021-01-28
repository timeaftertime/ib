package cn.milai.ib.loader.ex;

import cn.milai.ib.ex.IBException;

/**
 * 剧本资源文件不存在的异常
 * @author milai
 * @date 2020.02.21
 */
public class DramaResourceNotFoundException extends IBException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DramaResourceNotFoundException(String dramaCode, String resource) {
		super(String.format("剧本 %s 的资源文件 %s 未找到", dramaCode, resource));
	}

}
