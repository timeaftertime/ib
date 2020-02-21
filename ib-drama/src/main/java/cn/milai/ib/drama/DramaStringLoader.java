package cn.milai.ib.drama;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import cn.milai.ib.ex.IBException;
import cn.milai.ib.loader.DramaResourceLoader;
import cn.milai.ib.util.IOUtil;

/**
 * 国际化剧本字符串加载器
 * @author milai
 * @date 2020.02.21
 */
public class DramaStringLoader {

	private static final Logger log = LoggerFactory.getLogger(DramaStringLoader.class);

	/**
	 * 中文
	 */
	public final static String ZH_CN = "zh_cn";

	/**
	 * 英文
	 */
	public final static String EN_US = "en_us";

	private static String language = ZH_CN;

	/**
	 * dramaCode -> { lang -> { stringCode -> stringValue} }
	 */
	private static final Map<String, Map<String, Map<String, String>>> STRINGS = Maps.newHashMap();

	public void setLanguage(String lang) {
		if (!ZH_CN.equals(lang) && !EN_US.equals(lang)) {
			throw new IllegalAccessError("暂时只支持中文和英文：" + lang);
		}
		language = lang;
	}

	public static String get(String dramaCode, String stringCode) {
		return STRINGS
			.computeIfAbsent(dramaCode, c -> Maps.newHashMap())
			.computeIfAbsent(language, lang -> loadStrings(dramaCode, lang))
			.get(stringCode);
	}

	private static String getResourceName(String lang) {
		return String.format("/strings_%s.txt", lang);
	}

	private static Map<String, String> loadStrings(String dramaCode, String lang) {
		Map<String, String> strings = Maps.newHashMap();
		List<String> lines = IOUtil.toList(DramaResourceLoader.load(dramaCode, getResourceName(lang)));
		for (int i = 0; i < lines.size(); i++) {
			String[] splits = lines.get(i).split("=", 2);
			if (splits.length < 2) {
				log.error("剧本字符串文件定义错误：{}", lines.get(i));
				throw new IBException("剧本字符串文件定义错误：" + lines.get(i));
			}
			// 多行字符串
			String key = splits[0];
			if (splits[1].trim().equals("{")) {
				StringBuilder sb = new StringBuilder();
				for (i++; i < lines.size(); i++) {
					if (lines.get(i).trim().equals("}")) {
						addString(strings, key, sb.toString());
						break;
					}
					sb.append(lines.get(i)).append('\n');
				}
				continue;
			}
			// 单行字符串
			addString(strings, key, splits[1]);
		}
		return strings;
	}

	private static void addString(Map<String, String> strings, String key, String value) {
		if (strings.containsKey(key)) {
			log.warn("重复定义过字符串 {} ，进行覆盖操作：pre = {}, now = {}", key, strings.get(key), value);
		}
		strings.put(key, value);
	}
}
