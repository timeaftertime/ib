package cn.milai.ib.mode;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import cn.milai.ib.IBCore;
import cn.milai.ib.component.form.text.LinesFullScreenPassComponent;
import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.container.Container;
import cn.milai.ib.container.LifecycleContainer;
import cn.milai.ib.container.listener.ContainerEventListener;
import cn.milai.ib.container.listener.ContainerLifecycleListener;
import cn.milai.ib.drama.Drama;
import cn.milai.ib.drama.DramaResolver;
import cn.milai.ib.ex.IBContainerException;
import cn.milai.ib.loader.DramaResLoader;
import cn.milai.ib.util.IOUtil;
import cn.milai.ib.util.WaitUtil;

/**
 * 剧情模式
 * @author milai
 */
@Order(0)
@Component
public class StoryMode extends AbstractGameMode implements ContainerEventListener, ContainerLifecycleListener {

	private static final int SHOW_DRAMA_NAME_FRAMES = 60;
	private static final String STAGE_CONF_FILE = "/ib-stages.conf";
	private static final String THREAD_NAME_PREFIX = "Story#";

	private Container container;
	private List<String> dramaCodes;
	private List<DramaResolver> dramaResolvers;

	@Override
	public String name() {
		return "故事模式";
	}

	@Override
	public void init() {
		InputStream in = StoryMode.class.getResourceAsStream(STAGE_CONF_FILE);
		dramaCodes = IOUtil.toListFilter(in, line -> !line.startsWith("#"));
		dramaResolvers = IBCore.getBeansOrdered(DramaResolver.class);
		container = IBCore.getBean(Container.class);
	}

	@Override
	public void run() {
		try {
			((LifecycleContainer) container).start();
			for (int i = 0; i < dramaCodes.size() && !Thread.interrupted(); i++) {
				Drama drama = resolveDrama(dramaCodes.get(i));
				setName(THREAD_NAME_PREFIX + dramaCodes.get(i));
				LinesFullScreenPassComponent stageInfo = new LinesFullScreenPassComponent(
					SHOW_DRAMA_NAME_FRAMES,
					Arrays.asList("第 " + (i + 1) + " 关", drama.getName()),
					SystemConf.prorate(10),
					container);
				container.addObject(stageInfo);
				DramaResLoader.load(dramaCodes.get(i));
				WaitUtil.waitRemove(stageInfo, 5);
				drama.run(container);
				//			form.reset();
			}
		} catch (IBContainerException e) {
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
