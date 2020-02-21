package cn.milai.ib.drama.runtime;

import java.util.Map;

import cn.milai.ib.drama.clip.Clip;

/**
 * 剧本空间，存储一次剧本执行过程中需要的数据
 * 类似 JVM 的线程空间
 * 2019.12.14
 * @author milai
 */
public class DramaSpace {

	/**
	 * 剧情帧的栈
	 */
	private DramaStack stack = new DramaStack();

	private String dramaName;

	/**
	 * 程序计数器
	 */
	private int pc;

	public DramaSpace(String dramaCode) {
		Clip clip = callClip(dramaCode, null).getClip();
		dramaName = clip.getName();
	}

	/**
	 * 获取剧本名字
	 * @return
	 */
	public String getDramaName() {
		return dramaName;
	}

	/**
	 * 下一个要被执行指令位置
	 * @return
	 */
	public int getPC() {
		return pc;
	}

	/**
	 * 设置 PC
	 * @param pc
	 */
	public void setPC(int pc) {
		this.pc = pc;
	}

	/**
	 * 调用剧本片段
	 * @param clipCode {@code Clip } 接口实现类的全类名
	 */
	public Frame callClip(String clipCode, Map<String, String> params) {
		return stack.pushFrame(new Frame(clipCode, params, this));
	}

	public Frame popCurrentFrame() {
		return stack.popFrame();
	}

	/**
	 * 剧本是否执行完成
	 */
	public boolean isFinished() {
		return stack.isEmpty();
	}

	/**
	 * 获取当前正在执行的 Frame
	 * @return
	 */
	public Frame currentFrame() {
		return stack.currentFrame();
	}

}
