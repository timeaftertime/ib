package cn.milai.ib.drama.clip;

/**
 * 剧情片段，可在不同剧情中复用的片段
 * 类似于 Java 语言的方法
 * 2019.12.14
 * @author milai
 */
public interface Clip {

	/**
	 * 设置一个参数值
	 * @param key
	 * @param value
	 */
	void setVariable(String key, String value);

	/**
	 * 获取一个参数值
	 * @param key
	 * @return
	 */
	String getVariable(String key);

	/**
	 * 获取剧本的字节数据
	 * @return
	 */
	byte[] getBytes();

	/**
	 * 获取常量池中序号为 index 的 int 类型常量
	 * @param index
	 * @return
	 */
	int getIntConst(int index);

	/**
	 * 获取常量池中序号为 index 的 long 类型常量
	 * @param index
	 * @return
	 */
	long getLongConst(int index);

	/**
	 * 获取常量池中序号为 index 的 float 类型常量
	 * @param index
	 * @return
	 */
	float getFloatConst(int index);

	/**
	 * 获取常量池中序号为 index 的 M-UTF8字符串类型常量
	 * @param index
	 * @return
	 */
	String getUTF8Const(int index);

}
