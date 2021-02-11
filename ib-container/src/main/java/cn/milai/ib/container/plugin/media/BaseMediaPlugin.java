package cn.milai.ib.container.plugin.media;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import cn.milai.ib.container.ContainerClosedException;
import cn.milai.ib.container.plugin.BaseContainerPlugin;
import cn.milai.ib.container.plugin.PluginableContainer;
import cn.milai.ib.util.WaitUtil;

/**
 * {@link MediaPlugin} 默认实现
 * @author milai
 * @date 2021.02.09
 */
public class BaseMediaPlugin extends BaseContainerPlugin implements MediaPlugin {

	private static final String THREAD_NAME = "IBAudioPlay";
	private static final int THREAD_POOL_SIZE = 2;
	private Map<String, Audio> audios;

	public BaseMediaPlugin() {
		audios = Maps.newConcurrentMap();
	}

	@Override
	public final void playAudio(Audio audio) throws ContainerClosedException {
		getContainer().checkClosed();
		if (audio == null) {
			return;
		}
		audios.put(audio.getCode(), audio);
	}

	@Override
	public final void stopAudio(String code) throws ContainerClosedException {
		getContainer().checkClosed();
		audios.remove(code);
	}

	@Override
	public void onReset() {
		for (String code : audios.keySet()) {
			audios.remove(code);
		}
	}

	public void run(int startEpoch) {
		List<String> fetched = Lists.newArrayList();
		ExecutorService pool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
		while (startEpoch == getContainer().getEpoch() && isRunning()) {
			for (String code : Sets.newHashSet(audios.keySet())) {
				Audio audio = audios.get(code);
				if (audio == null) {
					continue;
				}
				// 判断 contains 和 add 操作都在 Audio 主线程，不需要同步
				if (!fetched.contains(audio.getCode())) {
					fetched.add(audio.getCode());
					pool.execute(() -> {
						audio.play();
						if (audio.isComplete()) {
							audios.remove(code, audio);
						}
						fetched.remove(audio.getCode());
					});
				}
			}
			PluginableContainer container = getContainer();
			if (container == null) {
				break;
			}
			WaitUtil.wait(container, 1L);
		}
		pool.shutdown();
	}

	@Override
	protected void onStart() {
		new Thread(() -> run(getContainer().getEpoch()), THREAD_NAME).start();
	}

	@Override
	protected void onStop() {
		audios.clear();
	}

}
