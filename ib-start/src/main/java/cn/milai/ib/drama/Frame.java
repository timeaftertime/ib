package cn.milai.ib.drama;

import java.util.Map;

/**
 * 剧本帧
 * 2019.12.08
 * @author milai
 */
public class Frame {

	/**
	 * 所属的 DramaSpace
	 */
	private DramaSpace dramaSpace;

	/**
	 * 对应的 Clip
	 */
	private Clip clip;

	/**
	 * 程序计数器
	 */
	private int pc;

	/**
	 * 将要被执行的指令的偏移量
	 * @return
	 */
	public int nextPC() {
		return pc;
	}

	/**
	 * 创建一个代表指定 clip 的帧
	 * @param clipCode
	 * @param params
	 */
	public Frame(String clipCode, Map<String, String> params, DramaSpace dramSpace) {
		this.clip = ClipFactory.newClip(clipCode, params);
		this.pc = 0;
		this.dramaSpace = dramSpace;
	}

	public Clip getClip() {
		return clip;
	}

	/**
	 * 获取帧所在的 DramaSpace
	 * @return
	 */
	public DramaSpace getSpace() {
		return dramaSpace;
	}

}
