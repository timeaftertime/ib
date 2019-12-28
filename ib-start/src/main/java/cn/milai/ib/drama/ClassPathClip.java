package cn.milai.ib.drama;

/**
 * 从 class path 加载剧本文件的 Clip 实现
 * 2019.12.24
 * @author milai
 */
public class ClassPathClip extends AbstractClip {

	public ClassPathClip(String clipCode) {
		super(ClassPathClip.class.getResource(toPath(clipCode)));
	}

	private static String toPath(String clipCode) {
		return "/dramas/" + clipCode.replace('.', '/') + ".drama";
	}

}
