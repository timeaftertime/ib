package cn.milai.ib.client.util;

public final class StringUtil {

	private StringUtil() {}
	
	/**
	 * 判断 str 不为 null 且的长度是否在 low 和 hig 之间（左开右闭）
	 * @param str
	 * @param low
	 * @param hig
	 * @return
	 */
	public static boolean lengthBetween(String str, int low, int hig) {
		if(str == null)
			return false;
		return low < str.length() && str.length() <= hig;
	}
	
}
