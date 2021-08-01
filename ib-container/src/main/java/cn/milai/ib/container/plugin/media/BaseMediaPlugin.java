package cn.milai.ib.container.plugin.media;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.milai.ib.container.ContainerClosedException;
import cn.milai.ib.container.Waits;
import cn.milai.ib.container.plugin.BaseContainerPlugin;
import cn.milai.ib.container.pluginable.PluginableContainer;

/**
 * {@link MediaPlugin} 默认实现
 * @author milai
 * @date 2021.02.09
 */
public class BaseMediaPlugin extends BaseContainerPlugin implements MediaPlugin {

	private static final String THREAD_NAME = "IBAudioPlay";
	private static final int THREAD_POOL_SIZE = 2;
	private Map<String, Audio> audios = new ConcurrentHashMap<>();

	@Override
	public final void playAudio(Audio audio) throws ContainerClosedException {
		container().checkClosed();
		if (audio == null) {
			return;
		}
		audios.put(audio.getCode(), audio);
	}

	@Override
	public final void stopAudio(String code) throws ContainerClosedException {
		container().checkClosed();
		audios.remove(code);
	}

	public void run(int startEpoch) {
		List<String> fetched = new ArrayList<>();
		ExecutorService pool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
		while (startEpoch == container().getEpoch() && isRunning()) {
			for (String code : new HashSet<>(audios.keySet())) {
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
			PluginableContainer container = container();
			if (container == null) {
				break;
			}
			Waits.wait(container, 1L);
		}
		pool.shutdown();
	}

	@Override
	protected void onStart() {
		new Thread(() -> run(container().getEpoch()), THREAD_NAME).start();
	}

	@Override
	protected void onStop() {
		audios.clear();
	}

}
