package cn.milai.ib.control;

import java.util.function.Consumer;

import cn.milai.common.thread.counter.Counter;
import cn.milai.common.thread.counter.DownCounter;

/**
 * 渐入渐出的透明度计算器
 * @author milai
 * @date 2020.04.06
 */
public class PassCaculator {

	public static final double MAX_TRANSPARENCY = 255.0;
	private double inDelta;
	private double outDelta;
	private int transparency;
	private Counter inCounter;
	private Counter keepCounter;
	private Counter outCounter;
	private Counter nowCounter;

	/**
	 * 创建一个指定渐入、保持、渐出帧数的透明度计算器
	 * @param inFrame
	 * @param keepFrame
	 * @param outFrame
	 */
	public PassCaculator(long inFrame, long keepFrame, long outFrame) {
		this(inFrame, keepFrame, outFrame, c -> {});
	}

	/**
	 * 创建一个指定渐入、保持、渐出帧数的透明计算器，计算完毕时将使用当前计算器调用指定回调函数
	 * @param inFrame
	 * @param keepFrame
	 * @param outFrame
	 * @param callback
	 */
	public PassCaculator(long inFrame, long keepFrame, long outFrame, Consumer<PassCaculator> callback) {
		nowCounter = inCounter = new DownCounter(inFrame, c -> nowCounter = keepCounter);
		keepCounter = new DownCounter(keepFrame, c -> nowCounter = outCounter);
		outCounter = new DownCounter(outFrame, c -> callback.accept(this));
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
		transparency = 0;
	}

	/**
	 * 是否处于透明度不变的阶段
	 * @return
	 */
	public boolean isKeep() { return inCounter.isMet() && !keepCounter.isMet(); }

	/**
	 * 计算下一透明度
	 */
	public void refresh() {
		nowCounter.count();
		if (!inCounter.isMet()) {
			transparency += inDelta;
			if (transparency > MAX_TRANSPARENCY) {
				transparency = (int) MAX_TRANSPARENCY;
			}
		} else if (keepCounter.isMet()) {
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
