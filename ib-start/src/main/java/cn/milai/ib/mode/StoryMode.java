package cn.milai.ib.mode;

import cn.milai.ib.conf.AudioConf;
import cn.milai.ib.drama.DramaStarter;

/**
 * 剧情模式
 * @author milai
 */
public class StoryMode extends GameMode {

	private DramaStarter drama;

	public StoryMode() {
		super();
		drama = new DramaStarter("cn.milai.ib.Welcome", form);
	}

	/**
	 * 停止 BGM
	 */
	protected void stopBGM() {
		form.stopAudio(AudioConf.BGM_CODE);
	}

	@Override
	public void run() {
		form.playAudio(AudioConf.BGM.newAudio());
		drama.start();
	}

	@Override
	protected void gameOver() {
		super.gameOver();
		form.stopAudio(AudioConf.BGM_CODE);
	}

	@Override
	public void onFormClosed() {
		// 窗口关闭时才会停止剧情
		drama.interrupt();
	}

}
