package cn.milai.ib.drama.runtime;

import java.util.Stack;

/**
 * 剧本帧的栈
 *
 * 2019.12.08
 *
 * @author milai
 */
public class DramaStack extends Stack<Frame> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 获取当前剧本帧
	 * 
	 * @return
	 */
	public Frame currentFrame() {
		return super.peek();
	}

	/**
	 * 压入新的剧本帧
	 * 
	 * @param frame
	 * @return
	 */
	public Frame pushFrame(Frame frame) {
		return super.push(frame);
	}

	/**
	 * 获取并弹出当前剧本帧
	 * @return
	 */
	public Frame popFrame() {
		return super.pop();
	}

}
