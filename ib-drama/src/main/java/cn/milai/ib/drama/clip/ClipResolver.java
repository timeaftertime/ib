package cn.milai.ib.drama.clip;

import java.io.InputStream;

/**
 * Clip code 解析器
 * 2020.01.20
 * @author milai
 */
public interface ClipResolver {

	/**
	 * 将 clipCode 解析为对应的输入流
	 * @param clipCode
	 * @return
	 */
	InputStream openStream(String clipCode);
}
