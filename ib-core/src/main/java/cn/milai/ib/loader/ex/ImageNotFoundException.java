package cn.milai.ib.loader.ex;

import cn.milai.ib.ex.IBIOException;

/**
 * 图片文件未找到异常
 * @author milai
 * @date 2020.02.01
 */
public class ImageNotFoundException extends IBIOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ImageNotFoundException(Class<?> clazz, String status) {
		super(String.format("角色图片不存在：character = %s, status = %s", clazz.getName(), status));
	}

}
