package cn.milai.ib.drama;

import java.net.URL;

/**
 * 从 class path 加载剧本文件的 Clip 实现
 * 2019.12.24
 * @author milai
 */
public class ClassPathClip extends AbstractClip {

	public ClassPathClip(String clipCode) {
		super(clipCode);
	}

	private static String toPath(String clipCode) {
		return "/dramas/" + clipCode.replace('.', '/') + ".drama";
	}

	@Override
	protected URL getClipURL(String clipCode) {
		return ClassPathClip.class.getResource(toPath(clipCode));
	}

}
