package cn.milai.ib.mode;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import cn.milai.ib.IBCore;
import cn.milai.ib.component.form.text.LinesFullScreenPassComponent;
import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.container.Audio;
import cn.milai.ib.drama.Drama;
import cn.milai.ib.drama.DramaResolver;
import cn.milai.ib.util.IOUtil;
import cn.milai.ib.util.TimeUtil;

/**
 * 剧情模式
 * @author milai
 */
@Order(0)
@Component
public class StoryMode extends AbstractGameMode {

	private static final int SHOW_DRAMA_NAME_FRAMES = 80;
	private static final String STAGE_CONF_FILE = "/ib-stages.conf";
	private static final String THREAD_NAME_PREFIX = "Story#";

	private List<String> dramaCodes;
	private List<DramaResolver> dramaResolvers;

	@Override
	public String name() {
		return "故事模式";
	}

	@Override
	public void initGameMode() {
		InputStream in = StoryMode.class.getResourceAsStream(STAGE_CONF_FILE);
		dramaCodes = IOUtil.toListFilter(in, line -> !line.startsWith("#"));
		dramaResolvers = IBCore.getBeansOrdered(DramaResolver.class);
	}

	/**
	 * 停止 BGM
	 */
	protected void stopBGM() {
		form.stopAudio(Audio.BGM_CODE);
	}

	@Override
	public void run() {
		form.start();
		for (int i = 0; i < dramaCodes.size() && !Thread.interrupted(); i++) {
			Drama drama = resolveDrama(dramaCodes.get(i));
			setName(THREAD_NAME_PREFIX + dramaCodes.get(i));
			form.addObject(new LinesFullScreenPassComponent(
				SHOW_DRAMA_NAME_FRAMES,
				Arrays.asList("第 " + (i + 1) + " 关", drama.getName()),
				SystemConf.prorate(10),
				form));
			TimeUtil.wait(form, SHOW_DRAMA_NAME_FRAMES);
			addPlayer();
			drama.run(form);
			//			form.reset();
		}
	}

	private Drama resolveDrama(String dramaCode) {
		for (DramaResolver resolver : dramaResolvers) {
			Drama drama = resolver.resolve(dramaCode);
			if (drama != null) {
				return drama;
			}
		}
		throw new DramaCanNotResolveException(dramaCode);
	}

	@Override
	public void onContainerClosed() {
		// 剧本执行线程与 StoryMode 为同一个线程，所以这里实际上是中断剧本和 StoryMode
		this.interrupt();
	}

}
