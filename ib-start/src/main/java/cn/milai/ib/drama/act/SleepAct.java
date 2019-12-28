package cn.milai.ib.drama.act;

import java.io.IOException;

import cn.milai.ib.constant.ActType;
import cn.milai.ib.container.Container;
import cn.milai.ib.drama.ByteReader;
import cn.milai.ib.drama.Frame;
import cn.milai.ib.util.TimeUtil;

/**
 * 使剧情休眠（无动作）指定帧数的动作
 *
 * 2019.12.20
 *
 * @author milai
 */
public class SleepAct extends AbstractAct {

	/**
	 * 应该休眠的帧数
	 */
	private long sleepFrame;

	@Override
	public ActType getCode() {
		return ActType.SLEEP;
	}

	@Override
	protected void action(Frame frame, Container container) throws Exception {
		TimeUtil.wait(container, sleepFrame);
	}

	@Override
	protected void readOperands(ByteReader reader) throws IOException {
		sleepFrame = reader.readInt64();
	}

}
