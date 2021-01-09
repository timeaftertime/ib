package cn.milai.ib.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;

import cn.milai.ib.ex.IBIOException;

/**
 * IO 相关工具类
 * 2020.01.05
 * @author milai
 */
public abstract class IOUtil {

	private static final Logger LOG = LoggerFactory.getLogger(IOUtil.class);

	private IOUtil() {
	}

	public static byte[] toBytes(InputStream in) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int count = 0;
		try {
			while ((count = (in.read(buf))) > 0) {
				out.write(buf, 0, count);
			}
			in.close();
			return out.toByteArray();
		} catch (IOException e) {
			LOG.error("读取输入流失败", e);
			throw new IBIOException(e);
		}
	}

	/**
	 * 从输入流中读取字节，直到填满字节数组或读完输入流
	 * 返回读取的字节数
	 * @param in
	 * @param data
	 * @return
	 */
	public static int toBytes(InputStream in, byte[] data) {
		int off = 0;
		int read = -1;
		int len = data.length;
		try {
			while (off < data.length && (read = in.read(data, off, len)) != -1) {
				off += read;
				len -= read;
			}
			return off;
		} catch (IOException e) {
			LOG.error("读取输入流失败", e);
			throw new IBIOException(e);
		}
	}

	public static InputStream toInputStream(byte[] data) {
		return new ByteArrayInputStream(data);
	}

	/**
	 * 以 UTF-8 编码逐行读取输入流
	 * 返回每行组成的 List
	 * @param in
	 * @return
	 */
	public static List<String> toList(InputStream in) {
		List<String> lines = Lists.newArrayList();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charsets.UTF_8))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
			in.close();
		} catch (IOException e) {
			LOG.error("读取输入流失败", e);
			throw new IBIOException(e);
		}
		return lines;
	}

	/**
	 * 以 UTF-8 编码逐行读取输入流
	 * 忽略前后空格，去掉不满足条件的行
	 * 返回每行组成的 List
	 * @param in
	 * @return
	 */
	public static List<String> toListFilter(InputStream in, Predicate<String> p) {
		return toList(in).stream().map(line -> line.trim()).filter(p).collect(Collectors.toList());
	}

	/**
	 * 以 UTF-8 编码逐行读取输入流
	 * 忽略前后空格，去掉不满足条件的行
	 * 返回每行组成的 List
	 * @param url
	 * @return
	 */
	public static List<String> toListFilter(URL url, Predicate<String> p) {
		try {
			return toListFilter(url.openStream(), p);
		} catch (IOException e) {
			LOG.error("打开输入流失败：url = {}, error = {}", url, ExceptionUtils.getStackTrace(e));
			throw new IBIOException(e);
		}
	}

	/**
	 * 以 UTF-8 读取输入流所有数据，并转换为以 \n 表示换行的整个字符串
	 * @param in
	 * @return
	 */
	public static String toString(InputStream in) {
		StringBuilder sb = new StringBuilder();
		for (String s : toList(in)) {
			sb.append(s).append('\n');
		}
		return sb.toString();
	}

	/**
	 * 以 UTF-8 读取输入流所有数据，并将所有满足条件 p 的行转换为以 \n 表示换行的整个字符串
	 * @param in
	 * @return
	 */
	public static String toStringFilter(InputStream in, Predicate<String> p) {
		StringBuilder sb = new StringBuilder();
		for (String s : toListFilter(in, p)) {
			sb.append(s).append('\n');
		}
		return sb.toString();
	}
}
