package cn.milai.ib.drama;

import java.util.Map;

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

	/**
	 * 程序计数器
	 */
	private int pc;

	public DramaSpace(String dramaCode) {
		callClip(dramaCode, null);
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
	public void callClip(String clipCode, Map<String, String> params) {
		stack.pushFrame(new Frame(clipCode, params, this));
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
