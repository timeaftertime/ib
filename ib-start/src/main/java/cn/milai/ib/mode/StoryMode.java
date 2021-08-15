package cn.milai.ib.mode;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import cn.milai.ib.IBCore;
import cn.milai.ib.container.ContainerClosedException;
import cn.milai.ib.container.DramaContainer;
import cn.milai.ib.container.Waits;
import cn.milai.ib.container.listener.LifecycleListener;
import cn.milai.ib.control.text.LinesFullScreenPass;
import cn.milai.ib.loader.DramaResLoader;
import cn.milai.ib.mode.drama.Drama;
import cn.milai.ib.mode.drama.DramaCanNotResolveException;
import cn.milai.ib.mode.drama.DramaResolver;

/**
 * 剧情模式
 * @author milai
 */
@Configuration
@Order(0)
@ConditionalOnBean(StoryConf.class)
public class StoryMode extends AbstractGameMode implements LifecycleListener {

	/**
	 * 默认宽度
	 */
	private static final int WIDTH = 554;

	/**
	 * 默认高度
	 */
	private static final int HEIGHT = 689;

	private static final int DRAMA_NAME_FRAMES = 20;
	private static final String STORY_THREAD = "Story#";

	private DramaContainer container;
	private String[] dramaCodes;
	private List<DramaResolver> dramaResolvers;

	@Override
	public String name() {
		return "剧情模式";
	}

	@Override
	public void init() {
		dramaCodes = IBCore.getBean(StoryConf.class).getStages();
		dramaResolvers = IBCore.getOrderedBeans(DramaResolver.class);
		container = IBCore.getBean(DramaContainer.class);
	}

	@Override
	public void run() {
		try {
			container.start();
			container.resizeWithUI(WIDTH, HEIGHT);
			for (int i = 0; i < dramaCodes.length; i++) {
				container.reset();
				LinesFullScreenPass loading = newFullScreenInfo(Arrays.asList("NOW LOADING……"));
				container.addObject(loading);
				Drama drama = resolveDrama(dramaCodes[i]);
				setName(STORY_THREAD + dramaCodes[i]);
				container.removeObject(loading);
				LinesFullScreenPass stageInfo = newFullScreenInfo(
					Arrays.asList("第 " + (i + 1) + " 关", drama.getName())
				);
				container.addObject(stageInfo);
				DramaResLoader.load(dramaCodes[i]);
				Waits.waitRemove(stageInfo, 5);
				drama.run(container);
			}
			container.reset();
			container.addObject(
				new LinesFullScreenPass(
					DRAMA_NAME_FRAMES, Integer.MAX_VALUE, 1, Arrays.asList("GAME OVER"), 7
				)
			);
		} catch (ContainerClosedException e) {
			// 容器关闭，结束游戏
			return;
		}
	}

	private LinesFullScreenPass newFullScreenInfo(List<String> lines) {
		return new LinesFullScreenPass(DRAMA_NAME_FRAMES, DRAMA_NAME_FRAMES, DRAMA_NAME_FRAMES, lines, 7);
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

}
