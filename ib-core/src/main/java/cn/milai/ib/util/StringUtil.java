package cn.milai.ib.util;

import java.util.List;

import com.google.common.collect.Lists;

public final class StringUtil {

	/**
	 * 配置中表示无限大
	 */
	private static final String INF = "INF";

	private StringUtil() {
	}

	/**
	 * 将表示整数的字符串转换为对应的整数值
	 * 对于 INF 将返回 Integer.MAX_VALUE
	 * 对于 0x 开头，将返回对应的 16 进制数，
	 * 否则返回对应的 10 进制数
	 * @param str
	 * @return
	 */
	public static final int parseInt(String str) {
		str = str.trim();
		if (INF.equalsIgnoreCase(str)) {
			return Integer.MAX_VALUE;
		}
		return str.startsWith("0x") ? Integer.parseInt(str.substring(2), 16) : Integer.parseInt(str);
	}

	/**
	 * 将表示长整数的字符串转换为对应的长整数值
	 * 对于 INF 将返回 Long.MAX_VALUE
	 * 对于 0x 开头，将返回对应的 16 进制数，
	 * 否则返回对应的 10 进制数
	 * @param str
	 * @return
	 */
	public static final long parseLong(String str) {
		str = str.trim();
		if (INF.equalsIgnoreCase(str)) {
			return Long.MAX_VALUE;
		}
		return str.startsWith("0x") ? Long.parseLong(str.substring(2), 16) : Long.parseLong(str);
	}

	/**
	 * 将字符串以换行切割，返回切割后字符串的列表
	 * @param str
	 * @return
	 */
	public static final List<String> lines(String str) {
		List<String> strs = Lists.newArrayList();
		for (String line : str.split("\n")) {
			strs.add(line);
		}
		return strs;
	}
}
