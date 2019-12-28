package cn.milai.ib.drama;

import java.io.IOException;

/**
 * 字节码读取器
 * 2019.12.16
 * @author milai
 */
public interface ByteReader {

	/**
	 * 读取1 个字节并转换为整数
	 * 
	 * @return
	 */
	byte readInt8() throws IOException;

	/**
	 * 读取1 个字节并转换为无符号整数
	 * 
	 * @return
	 */
	int readUint8() throws IOException;

	/**
	 * 读取 2 个字节并转换为整数
	 * @return
	 */
	short readInt16() throws IOException;

	/**
	 * 读取 2 个字节并转换为无符号整数
	 * @return
	 */
	int readUint16() throws IOException;

	/**
	 * 读取 4 个字节并转换为整数
	 * @return
	 */
	int readInt32() throws IOException;

	/**
	 * 读取 4 个字节并转换为 float 类型
	 * @return
	 */
	float readFloat() throws IOException;
	
	/**
	 * 读取 8 个字符并转换为 long 类型
	 * @return
	 * @throws IOException
	 */
	long readInt64() throws IOException;

	/**
	 * 读取一个 M-UTF8 字符串
	 * @return
	 */
	String readUTF8() throws IOException;

	/**
	 * 重新设置读指针位置
	 * @param offset
	 */
	void setPos(int offset) throws IOException;

}
