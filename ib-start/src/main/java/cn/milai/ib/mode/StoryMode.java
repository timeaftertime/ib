package cn.milai.ib.mode;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import cn.milai.ib.IBBeans;
import cn.milai.ib.container.ContainerClosedException;
import cn.milai.ib.container.Stage;
import cn.milai.ib.container.Waits;
import cn.milai.ib.container.listener.LifecycleListener;
import cn.milai.ib.control.stage.Curtain;
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

	private Stage stage;
	private String[] dramaCodes;
	private List<DramaResolver> dramaResolvers;

	@Override
	public String name() {
		return "剧情模式";
	}

	@Override
	public void init() {
		dramaCodes = IBBeans.getBean(StoryConf.class).getStages();
		dramaResolvers = IBBeans.getOrderedBeans(DramaResolver.class);
		stage = IBBeans.getBean(Stage.class);
	}

	@Override
	public void run() {
		try {
			stage.start();
			stage.resize(WIDTH, HEIGHT);
			for (int i = 0; i < dramaCodes.length; i++) {
				stage.reset();
				Curtain loading = newFullScreenInfo(Arrays.asList("NOW LOADING……"));
				stage.addObject(loading);
				Drama drama = resolveDrama(dramaCodes[i]);
				setName(STORY_THREAD + dramaCodes[i]);
				stage.removeObject(loading);
				Curtain stageInfo = newFullScreenInfo(
					Arrays.asList("第 " + (i + 1) + " 关", drama.getName())
				);
				stage.addObject(stageInfo);
				DramaResLoader.load(dramaCodes[i]);
				Waits.waitRemove(stageInfo, 5);
				drama.run(stage);
			}
			stage.reset();
			stage.addObject(
				new Curtain(
					DRAMA_NAME_FRAMES, Integer.MAX_VALUE, 1, Arrays.asList("GAME OVER"), 7
				)
			);
		} catch (ContainerClosedException e) {
			// 容器关闭，结束游戏
			return;
		}
	}

	private Curtain newFullScreenInfo(List<String> lines) {
		return new Curtain(DRAMA_NAME_FRAMES, DRAMA_NAME_FRAMES, DRAMA_NAME_FRAMES, lines, 7);
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
