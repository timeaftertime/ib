package cn.milai.ib.drama.clip;

import java.util.Map;

import cn.milai.ib.drama.ex.ClipParamInvalidExcecption;

/**
 * 产生 Clip 的工厂类
 * 2019.12.14
 * @author milai
 */
public class ClipFactory {

	private static ClipResolver resolver = new ClassPathClipResolver();

	/**
	 * 设置使用的剧本解析器
	 * @param resolver
	 */
	public static void setClipResolver(ClipResolver resolver) {
		ClipFactory.resolver = resolver;
	}

	/**
	 * 加载指定的剧本片段
	 * @param clipCode
	 * @param params 需要传给剧本片段的参数，为 null 表示不需要传递
	 * @return
	 */
	public static Clip newClip(String clipCode, Map<String, String> params) {
		Clip clip = new DefaultClip(resolver.openStream(clipCode));
		copyParams(clip, params);
		return clip;
	}

	/**
	 * 检查并复制 params 中属性到 clip
	 * @param clip
	 * @param params 需要复制的参数 map ，若为 null 表示不需要复制
	 * @return
	 */
	private static final void copyParams(Clip clip,
			Map<String, String> params) {
		if (params == null) {
			return;
		}
		for (String key : params.keySet()) {
			String value = params.get(key);
			if (value == null) {
				throw new ClipParamInvalidExcecption(key, value);
			}
			clip.setVariable(key, value);
		}
	}

}
