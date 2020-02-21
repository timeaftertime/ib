package cn.milai.ib.mode;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

import cn.milai.ib.component.form.LinesFullScreenPassComponent;
import cn.milai.ib.conf.AudioConf;
import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.drama.DramaInterpreter;
import cn.milai.ib.util.IOUtil;
import cn.milai.ib.util.TimeUtil;

/**
 * 剧情模式
 * @author milai
 */
public class StoryMode extends GameMode {

	private static final int SHOW_DRAMA_NAME_FRAMES = 80;
	private static final String STAGE_CONF_FILE = "/stages.conf";
	private static final String THREAD_NAME_PREFIX = "Story#";

	private List<String> dramaCodes;

	public StoryMode() {
		dramaCodes = Lists.newArrayList();
		InputStream in = StoryMode.class.getResourceAsStream(STAGE_CONF_FILE);
		dramaCodes = IOUtil.toListFilter(in, line -> !line.startsWith("#"));
	}

	/**
	 * 停止 BGM
	 */
	protected void stopBGM() {
		form.stopAudio(AudioConf.BGM_CODE);
	}

	@Override
	public void run() {
		for (int i = 0; i < dramaCodes.size() && !Thread.interrupted(); i++) {
			DramaInterpreter drama = new DramaInterpreter(dramaCodes.get(i), form);
			setName(THREAD_NAME_PREFIX + dramaCodes.get(i));
			form.addObject(
				new LinesFullScreenPassComponent(SHOW_DRAMA_NAME_FRAMES,
					Arrays.asList(
						"第 " + (i + 1) + " 关",
						drama.getDramaName()), SystemConf.prorate(10),
					form));
			TimeUtil.wait(form, SHOW_DRAMA_NAME_FRAMES);
			addPlayer();
			drama.run();
			//			form.reset();
		}
	}

	@Override
	protected void gameOver() {
		super.gameOver();
		form.stopAudio(AudioConf.BGM_CODE);
	}

	@Override
	public void onFormClosed() {
		// 窗口关闭时才会停止剧情
		this.interrupt();
	}

}
