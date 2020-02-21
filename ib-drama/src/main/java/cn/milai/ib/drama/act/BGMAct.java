package cn.milai.ib.drama.act;

import java.io.IOException;
import java.io.InputStream;

import cn.milai.ib.IBCore;
import cn.milai.ib.constant.ActType;
import cn.milai.ib.container.AudioCreator;
import cn.milai.ib.container.Container;
import cn.milai.ib.drama.runtime.Frame;
import cn.milai.ib.drama.util.ByteReader;
import cn.milai.ib.loader.DramaResourceLoader;

/**
 * 设置 BGM 的指令
 * @author milai
 * @date 2020.02.19
 */
public class BGMAct extends AbstractAct {

	private static final String BGM_CODE = "BGM";

	private int resourceIndex;

	private AudioCreator audioCreator = IBCore.getBean(AudioCreator.class);

	@Override
	public ActType getCode() {
		return ActType.BGM;
	}

	@Override
	protected void action(Frame frame, Container container) throws Exception {
		String resource = frame.getClip().getUTF8Const(resourceIndex);
		InputStream in = DramaResourceLoader.load(frame.getClip().getCode(), resource);
		container.playAudio(audioCreator.newAudio(BGM_CODE, in));
	}

	@Override
	protected void readOperands(ByteReader reader) throws IOException {
		resourceIndex = reader.readUint16();
	}

}
