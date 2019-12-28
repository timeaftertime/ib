package cn.milai.ib.drama;

import java.io.IOException;

import cn.milai.ib.container.Container;
import cn.milai.ib.drama.act.Act;
import cn.milai.ib.drama.act.ActFactory;

/**
 * 剧情解释器
 * 2019.12.08
 * @author milai
 */
public class DramaInterpreter implements Runnable {

	private volatile boolean interrupted;
	private Container container;
	private DramaSpace dramaSpace;

	public DramaInterpreter(String dramaCode, Container container) {
		interrupted = false;
		dramaSpace = new DramaSpace(dramaCode);
		this.container = container;
	}

	/**
	 * 中断当前剧本
	 * 只有处于可中断状态（执行完一条指令后）才能被中断
	 * 否则剧本会继续到可中断为止
	 */
	public void interrunpt() {
		this.interrupted = true;
	}

	/**
	 * 剧本的执行
	 */
	@Override
	public void run() {
		try {
			while (!dramaSpace.isFinished() && !interrupted) {
				beforeAction();
				nextAct();
				afterAction();
			}
		} catch (IOException e) {
			// 执行剧本时，剧本已经在内存中，不太可能发生 IO 异常
			// 所以这里简单忽略
			e.printStackTrace();
		}
	}

	/**
	 * 读取并执行下一个动作
	 * @param dramaSpace
	 * @param container
	 * @throws IOException 
	 */
	private void nextAct() throws IOException {
		Frame currentFrame = dramaSpace.currentFrame();
		Clip clip = currentFrame.getClip();
		Act act = ActFactory.create(clip.readUint8());
		act.execute(currentFrame, container);
	}

	private void beforeAction() throws IOException {
		// 读取下一个动作前，设置当前帧将被读取的动作指令是 PC 所指位置
		dramaSpace.currentFrame().getClip().setPos(dramaSpace.getPC());
	}

	private void afterAction() {
		if (dramaSpace.isFinished()) {
			return;
		}
		// 调用了新的 Clip 或执行跳转指令后，需要设置 PC 为当前帧的 PC
		dramaSpace.setPC(dramaSpace.currentFrame().nextPC());
	}

}
