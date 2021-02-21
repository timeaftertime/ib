package cn.milai.ib.mode;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import cn.milai.common.io.InputStreams;
import cn.milai.ib.IBCore;
import cn.milai.ib.component.text.LinesFullScreenPass;
import cn.milai.ib.container.ContainerClosedException;
import cn.milai.ib.container.DramaContainer;
import cn.milai.ib.container.lifecycle.LifecycleListener;
import cn.milai.ib.drama.Drama;
import cn.milai.ib.drama.DramaResolver;
import cn.milai.ib.loader.DramaResLoader;
import cn.milai.ib.util.WaitUtil;

/**
 * 剧情模式
 * @author milai
 */
@Order(0)
@Component
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
	private static final String STAGE_CONF_FILE = "/ib-stages.conf";
	private static final String THREAD_NAME_PREFIX = "Story#";

	private DramaContainer container;
	private List<String> dramaCodes;
	private List<DramaResolver> dramaResolvers;

	@Override
	public String name() {
		return "故事模式";
	}

	@Override
	public void init() {
		InputStream in = StoryMode.class.getResourceAsStream(STAGE_CONF_FILE);
		dramaCodes = InputStreams.readLines(in).stream()
			.map(line -> line.trim())
			.filter(line -> !line.startsWith("#"))
			.collect(Collectors.toList());
		dramaResolvers = IBCore.getBeansOrdered(DramaResolver.class);
		container = IBCore.getBean(DramaContainer.class);
	}

	@Override
	public void run() {
		try {
			container.start();
			for (int i = 0; i < dramaCodes.size() && !Thread.interrupted(); i++) {
				container.reset();
				container.resizeWithUI(WIDTH, HEIGHT);
				LinesFullScreenPass loading = new LinesFullScreenPass(
					SHOW_DRAMA_NAME_FRAMES, Arrays.asList("NOW LOADING……"), 7, container
				);
				container.addObject(loading);
				Drama drama = resolveDrama(dramaCodes.get(i));
				setName(THREAD_NAME_PREFIX + dramaCodes.get(i));
				container.removeObject(loading);
				LinesFullScreenPass stageInfo = new LinesFullScreenPass(
					SHOW_DRAMA_NAME_FRAMES, Arrays.asList("第 " + (i + 1) + " 关", drama.getName()), 7, container
				);
				container.addObject(stageInfo);
				DramaResLoader.load(dramaCodes.get(i));
				WaitUtil.waitRemove(stageInfo, 5);
				drama.run(container);
			}
		} catch (ContainerClosedException e) {
			// 容器关闭，结束游戏
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
		this.interrupt();
	}

}
