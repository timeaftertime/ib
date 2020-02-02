package cn.milai.ib.drama.clip;

/**
 * 剧本数据装饰器
 * @author milai
 * @date 2020.02.02
 */
public interface ClipDecorator {

	/**
	 * 获取装饰后的剧本数据
	 * @param data
	 * @return
	 */
	byte[] decorate(byte[] data);
}
