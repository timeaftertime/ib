package cn.milai.ib.drama;

/**
 * 剧情片段，可在不同剧情中复用的片段
 * 类似于 Java 语言的方法
 *
 * 2019.12.14
 *
 * @author milai
 */
public interface Clip extends ByteReader {

	/**
	 * 设置一个参数值
	 * 
	 * @param key
	 * @param value
	 */
	void setVariable(String key, String value);

	/**
	 * 获取一个参数值
	 * 
	 * @param key
	 * @return
	 */
	String getVariable(String key);

}
