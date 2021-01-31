package cn.milai.ib.component;

/**
 * 渐入渐出的透明度计算器
 * @author milai
 * @date 2020.04.06
 */
public class PassCaculator {

	public static final int MAX_TRANSPARENCY = 255;
	private long frame;
	private long inEndFrame;
	private long outStartFrame;
	private long inDelta;
	private long outDelta;
	private int transparency;
	private long exitFrame;

	/**
	 * 创建一个指定渐入、保持、渐出帧数的透明度计算器
	 * @param inFrame
	 * @param keepFrame
	 * @param outFrame
	 */
	public PassCaculator(long inFrame, long keepFrame, long outFrame) {
		frame = 0;
		inEndFrame = inFrame;
		outStartFrame = inEndFrame + keepFrame;
		if (inFrame != 0) {
			inDelta = MAX_TRANSPARENCY / inFrame + 1;
		} else {
			inDelta = MAX_TRANSPARENCY;
		}
		if (outFrame != 0) {
			outDelta = MAX_TRANSPARENCY / outFrame + 1;
		} else {
			outDelta = MAX_TRANSPARENCY;
		}
		exitFrame = outStartFrame + outFrame;
		transparency = 0;
	}

	public boolean isEnd() { return frame >= exitFrame; }

	/**
	 * 是否处于透明度不变的阶段
	 * @return
	 */
	public boolean isKeep() { return frame >= inEndFrame && frame < outStartFrame; }

	/**
	 * 计算下一透明度
	 */
	public void refresh() {
		frame++;
		if (frame < inEndFrame) {
			transparency += inDelta;
			if (transparency > MAX_TRANSPARENCY) {
				transparency = MAX_TRANSPARENCY;
			}
		} else if (frame >= outStartFrame) {
			transparency -= outDelta;
			if (transparency < 0) {
				transparency = 0;
			}
		}
	}

	/**
	 * 获取此时的透明度 (0完全透明 ~ 255完全不透明)
	 * @return
	 */
	public int getTransparency() { return transparency; }

}
