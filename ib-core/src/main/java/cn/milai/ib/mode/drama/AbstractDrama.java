package cn.milai.ib.mode.drama;

import cn.milai.ib.config.ItemConfigApplier;
import cn.milai.ib.container.DramaContainer;
import cn.milai.ib.container.plugin.media.Audio;
import cn.milai.ib.container.plugin.ui.Image;
import cn.milai.ib.loader.AudioLoader;
import cn.milai.ib.loader.DramaStringLoader;
import cn.milai.ib.loader.ImageLoader;

/**
 * 剧本抽象基类
 * @author milai
 * @date 2020.03.06
 */
public abstract class AbstractDrama implements Drama, ItemConfigApplier {

	@Override
	public Image image(String resource) {
		return ImageLoader.load(getCode(), resource);
	}

	@Override
	public Audio audio(String audioCode, String resource) {
		return AudioLoader.load(audioCode, getCode(), resource);
	}

	@Override
	public String str(String strCode) {
		return DramaStringLoader.get(getCode(), strCode);
	}

	@Override
	public final void run(DramaContainer container) {
		container.newSize(initW(), initH());
		container.newUISize(initW(), initH());
		doRun(container);
		container.restoreSize();
		container.restoreUISize();
	}

	/**
	 * 设置好容器宽度和高度（实际的和显示的）后执行，执行完后将还原之前的宽度和高度
	 * @param container
	 */
	protected abstract void doRun(DramaContainer container);

	/**
	 * 获取剧本执行时容器的初始宽度
	 * @return
	 */
	protected abstract int initW();

	/**
	 * 获取剧本执行时容器的初始长度
	 * @return
	 */
	protected abstract int initH();

}
