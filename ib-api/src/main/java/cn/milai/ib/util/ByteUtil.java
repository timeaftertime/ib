package cn.milai.ib.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.milai.ib.ex.IBIOException;

/**
 * 字节相关工具类
 * 2020.01.05
 * @author milai
 */
public abstract class ByteUtil {

	private ByteUtil() {

	}

	public static byte[] toBytes(InputStream in) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int count = 0;
		try {
			while ((count = (in.read(buf))) > 0) {
				out.write(buf, 0, count);
			}
			return out.toByteArray();
		} catch (IOException e) {
			throw new IBIOException(e);
		}
	}

	public static InputStream toInputStream(byte[] data) {
		return new ByteArrayInputStream(data);
	}
}
