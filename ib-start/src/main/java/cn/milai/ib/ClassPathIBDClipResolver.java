package cn.milai.ib;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import cn.milai.ib.compiler.SimpleCompiler;
import cn.milai.ib.drama.clip.ClipResolver;
import cn.milai.ib.drama.ex.ClipNotFoundException;
import cn.milai.ib.drama.ex.ClipReadException;

/**
 * 从 class path 获取 .ibd 文件并编译的剧本解析器
 * 2020.01.20
 * @author milai
 */
public class ClassPathIBDClipResolver implements ClipResolver {

	@Override
	public InputStream openStream(String clipCode) {
		URL ibd = ClassPathIBDClipResolver.class.getResource(codeToPath(clipCode));
		if (ibd == null) {
			throw new ClipNotFoundException(clipCode);
		}
		return compile(ibd);
	}

	/**
	 * 编译 .ibd 文件并返回编译成功的输入流
	 * @param url
	 */
	private static InputStream compile(URL url) {
		try {
			return new ByteArrayInputStream(SimpleCompiler.compile(url.openStream()));
		} catch (IOException e) {
			throw new ClipReadException(url.toString());
		}
	}

	private static String codeToPath(String clipCode) {
		return "/dramas/" + clipCode.replace('.', '/') + ".ibd";
	}

}
