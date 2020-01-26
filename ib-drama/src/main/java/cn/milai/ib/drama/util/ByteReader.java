package cn.milai.ib.drama.util;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import cn.milai.ib.ex.IBIOException;
import cn.milai.ib.util.ByteUtil;

/**
 * 字节码读取器
 * 2019.12.16
 * @author milai
 */
public class ByteReader {

	private DataInputStream reader;
	private int pc;
	private byte[] bytes;

	public ByteReader() {
		this(new byte[0]);
	}

	public ByteReader(byte[] bytes) {
		reset(bytes, 0);
	}

	public ByteReader(InputStream in) {
		this(ByteUtil.toBytes(in));
	}

	/**
	 * 读取1 个字节并转换为整数
	 * @return
	 */
	public byte readInt8() throws IOException {
		pc++;
		return reader.readByte();
	}

	/**
	 * 读取1 个字节并转换为无符号整数
	 * @return
	 */
	public int readUint8() throws IOException {
		pc++;
		return reader.readUnsignedByte();
	}

	/**
	 * 读取 2 个字节并转换为整数
	 * @return
	 */
	public short readInt16() throws IOException {
		pc += 2;
		return reader.readShort();
	}

	/**
	 * 读取 2 个字节并转换为无符号整数
	 * @return
	 */
	public int readUint16() throws IOException {
		pc += 2;
		return reader.readUnsignedShort();
	}

	/**
	 * 读取 4 个字节并转换为整数
	 * @return
	 */
	public int readInt32() throws IOException {
		pc += 4;
		return reader.readInt();
	}

	/**
	 * 读取 4 个字节并转换为 float 类型
	 * @return
	 */
	public float readFloat() throws IOException {
		pc += 4;
		return reader.readFloat();
	}

	/**
	 * 读取 8 个字符并转换为 long 类型
	 * @return
	 * @throws IOException
	 */
	public long readInt64() throws IOException {
		pc += 8;
		return reader.readLong();
	}

	/**
	 * 重新设置字节数据和读指针位置
	 * @param offset
	 */
	public void reset(byte[] bytes, int offset) {
		this.bytes = Arrays.copyOf(bytes, bytes.length);
		reader = new DataInputStream(new ByteArrayInputStream(this.bytes));
		try {
			reader.skip(offset);
		} catch (IOException e) {
			// 不可能发生
			throw new IBIOException(e);
		}
		pc = offset;
	}

	/**
	 * 获取当前读指针位置
	 * @return
	 */
	public int getPC() {
		return pc;
	}

	/**
	 * 返回剩余的所有字节的数组
	 * @return
	 * @throws IOException 
	 */
	public byte[] readAll() throws IOException {
		int left = bytes.length - pc;
		pc = bytes.length;
		byte[] leftBytes = new byte[left];
		reader.read(leftBytes);
		return leftBytes;
	}
	
	/**
	 * 获取当前 reader 是否还有可以读取的数据
	 * @return
	 */
	public boolean hasMore() {
		return pc < bytes.length;
	}

}
