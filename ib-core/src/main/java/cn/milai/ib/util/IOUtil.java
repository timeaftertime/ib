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

	private static final Logger log = LoggerFactory.getLogger(IOUtil.class);

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
			log.error("读取输入流失败", e);
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
			log.error("读取输入流失败", e);
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
			log.error("打开输入流失败：url = {}, error = {}", url, ExceptionUtils.getStackTrace(e));
			throw new IBIOException(e);
		}
	}
}
