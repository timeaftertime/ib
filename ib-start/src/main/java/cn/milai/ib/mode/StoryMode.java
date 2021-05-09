package cn.milai.ib.mode;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import cn.milai.ib.IBCore;
import cn.milai.ib.container.ContainerClosedException;
import cn.milai.ib.container.DramaContainer;
import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.listener.LifecycleListener;
import cn.milai.ib.control.text.LinesFullScreenPass;
import cn.milai.ib.drama.Drama;
import cn.milai.ib.drama.DramaResolver;
import cn.milai.ib.loader.DramaResLoader;
import cn.milai.ib.util.Waits;

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

	private static final int SHOW_DRAMA_NAME_FRAMES = 60;
	private static final String THREAD_NAME_PREFIX = "Story#";

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
		dramaResolvers = IBCore.getBeansOrdered(DramaResolver.class);
		container = IBCore.getBean(DramaContainer.class);
	}

	@Override
	public void run() {
		try {
			container.start();
			for (int i = 0; i < dramaCodes.length && !Thread.interrupted(); i++) {
				container.reset();
				container.resizeWithUI(WIDTH, HEIGHT);
				LinesFullScreenPass loading = new LinesFullScreenPass(
					SHOW_DRAMA_NAME_FRAMES, Arrays.asList("NOW LOADING……"), 7, container
				);
				container.addObject(loading);
				Drama drama = resolveDrama(dramaCodes[i]);
				setName(THREAD_NAME_PREFIX + dramaCodes[i]);
				container.removeObject(loading);
				LinesFullScreenPass stageInfo = new LinesFullScreenPass(
					SHOW_DRAMA_NAME_FRAMES, Arrays.asList("第 " + (i + 1) + " 关", drama.getName()), 7, container
				);
				container.addObject(stageInfo);
				DramaResLoader.load(dramaCodes[i]);
				Waits.waitRemove(stageInfo, 5);
				drama.run(container);
			}
		} catch (ContainerClosedException e) {
			// 容器关闭，结束游戏
			return;
		}
		container.reset();
		container.resizeWithUI(WIDTH, HEIGHT);
		LinesFullScreenPass loading = new LinesFullScreenPass(
			SHOW_DRAMA_NAME_FRAMES, Integer.MAX_VALUE, 1, Arrays.asList("GAME OVER"), 7, container
		);
		container.addObject(loading);
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
	public void onClosed(LifecycleContainer container) {
		this.interrupt();
	}

}
