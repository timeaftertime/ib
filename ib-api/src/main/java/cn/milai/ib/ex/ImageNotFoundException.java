package cn.milai.ib.ex;

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

	public ImageNotFoundException(String characterId) {
		super(String.format("角色 %s 的图片未找到", characterId));
	}

}
