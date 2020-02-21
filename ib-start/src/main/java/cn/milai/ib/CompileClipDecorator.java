package cn.milai.ib;

import cn.milai.ib.compiler.SimpleCompiler;
import cn.milai.ib.drama.clip.ClipDecorator;
import cn.milai.ib.util.IOUtil;

/**
 * 编译剧本的剧本装饰器
 * @author milai
 * @date 2020.02.02
 */
public class CompileClipDecorator implements ClipDecorator {

	@Override
	public byte[] decorate(byte[] data) {
		return SimpleCompiler.compile(IOUtil.toInputStream(data));
	}

}
