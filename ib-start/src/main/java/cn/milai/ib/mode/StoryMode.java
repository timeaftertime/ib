package cn.milai.ib.mode;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import cn.milai.ib.IBBeans;
import cn.milai.ib.actor.prop.Curtain;
import cn.milai.ib.lifecycle.LifecycleLoopGroup;
import cn.milai.ib.loader.DramaResLoader;
import cn.milai.ib.mode.drama.Drama;
import cn.milai.ib.mode.drama.DramaCanNotResolveException;
import cn.milai.ib.mode.drama.DramaResolver;
import cn.milai.ib.stage.Stage;
import cn.milai.ib.stage.Waits;

/**
 * 剧情模式
 * @author milai
 */
@Configuration
@Order(0)
@ConditionalOnBean(StoryConf.class)
public class StoryMode extends AbstractGameMode {

	private Logger LOG = LoggerFactory.getLogger(StoryMode.class);

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
		LifecycleLoopGroup eventLoopGroup = new LifecycleLoopGroup(1);
		eventLoopGroup.next().register(stage.lifecycle()).awaitUninterruptibly();
		try {
			stage.resize(WIDTH, HEIGHT);
			for (int i = 0; i < dramaCodes.length; i++) {
				stage.clearActor();
				stage.lifecycle().reset();
				Curtain loading = newFullScreenInfo(Arrays.asList("NOW LOADING……"));
				stage.addActor(loading);
				Drama drama = resolveDrama(dramaCodes[i]);
				setName(STORY_THREAD + dramaCodes[i]);
				stage.removeActorSync(loading);
				Curtain stageInfo = newFullScreenInfo(Arrays.asList("第 " + (i + 1) + " 关", drama.getName()));
				stage.addActorSync(stageInfo);
				DramaResLoader.load(dramaCodes[i]);
				Waits.waitRemove(stageInfo);
				drama.run(stage);
			}
			stage.clearActor();
			stage.lifecycle().reset();
			stage.addActor(
				new Curtain(DRAMA_NAME_FRAMES, Integer.MAX_VALUE, 1, Arrays.asList("GAME OVER"), 7)
			);
		} catch (IllegalStateException e) {
			LOG.info("剧本执行中断", e);
		} finally {
			eventLoopGroup.shutdownGracefully();
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
