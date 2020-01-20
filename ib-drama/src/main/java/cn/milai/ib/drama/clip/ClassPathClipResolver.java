package cn.milai.ib.drama.clip;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import cn.milai.ib.drama.ex.ClipNotFoundException;
import cn.milai.ib.drama.ex.ClipReadException;

/**
 * 从 class path 获取资源的 Clip code 解析器
 * 2020.01.20
 * @author milai
 */
public class ClassPathClipResolver implements ClipResolver {

	@Override
	public InputStream openStream(String clipCode) {
		String path = codeToPath(clipCode);
		try {
			URL url = ClassPathClipResolver.class.getResource(path);
			if (url == null) {
				throw new ClipNotFoundException(clipCode);
			}
			return url.openStream();
		} catch (IOException e) {
			throw new ClipReadException("classpath: " + path);
		}
	}

	private String codeToPath(String clipCode) {
		return "/dramas/" + clipCode.replace('.', '/') + ".drama";
	}

}
