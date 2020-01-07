package cn.milai.ib.drama;

import java.io.IOException;

import cn.milai.ib.container.Container;
import cn.milai.ib.drama.act.Act;
import cn.milai.ib.drama.act.ActFactory;
import cn.milai.ib.drama.runtime.DramaSpace;
import cn.milai.ib.drama.runtime.Frame;
import cn.milai.ib.drama.util.ByteReader;

/**
 * 剧情解释器
 * 2019.12.08
 * @author milai
 */
public class DramaInterpreter implements Runnable {

	private volatile boolean interrupted;
	private Container container;
	private DramaSpace dramaSpace;
	private ByteReader reader;

	public DramaInterpreter(String dramaCode, Container container) {
		interrupted = false;
		dramaSpace = new DramaSpace(dramaCode);
		reader = new ByteReader();
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
				Frame frame = dramaSpace.currentFrame();
				Clip clip = frame.getClip();
				reader.reset(clip.getBytes(), frame.getPC());
				// 临时结束帧执行的方案，类似 return 命令
				if (!reader.hasMore()) {
					dramaSpace.popCurrentFrame();
					continue;
				}
				frame.synchronizeDramaSpacePC();
				Act act = ActFactory.create(reader.readUint8());
				act.initiailze(reader);
				frame.setPC(reader.getPC());
				act.execute(frame, container);
			}
		} catch (IOException e) {
			// 执行剧本时，剧本已经在内存中，不太可能发生 IO 异常
			// 所以这里简单忽略
			e.printStackTrace();
		}
	}

}
