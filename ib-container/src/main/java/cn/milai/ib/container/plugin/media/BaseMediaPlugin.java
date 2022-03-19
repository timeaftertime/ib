package cn.milai.ib.container.plugin.media;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.milai.common.thread.Condition;
import cn.milai.common.thread.counter.BlockDownCounter;
import cn.milai.ib.container.ContainerClosedException;
import cn.milai.ib.container.Waits;
import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.plugin.BaseExclusiveContainerPlugin;
import cn.milai.ib.container.pluginable.PluginableContainer;

/**
 * {@link MediaPlugin} 默认实现
 * @author milai
 * @date 2021.02.09
 */
public class BaseMediaPlugin extends BaseExclusiveContainerPlugin implements MediaPlugin {

	private static final String THREAD_NAME = "BaseMediaPlugin#MainLoop";

	private static final int THREAD_POOL_SIZE = 2;

	private Condition waitPlug;
	private Map<String, Audio> audios = new ConcurrentHashMap<>();

	@Override
	public final void playAudio(Audio audio) {
		if (audio == null) {
			return;
		}
		audios.put(audio.getCode(), audio);
	}

	@Override
	public final void stopAudio(String code) {
		audios.remove(code);
	}

	public void run() {
		Set<String> fetched = new HashSet<>();
		ExecutorService pool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
		while (!isDestroyed()) {
			PluginableContainer container = container();
			if (container == null) {
				waitPlug.await();
				continue;
			}
			for (String code : audios.keySet()) {
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
			Waits.wait(container, 1L);
		}
		pool.shutdown();
	}

	@Override
	public void onStart(LifecycleContainer container) {
		waitPlug = new BlockDownCounter(1);
		new Thread(this::run, THREAD_NAME).start();
	}

	@Override
	public void onEpochChanged(LifecycleContainer container) {
		clearAudio();
	}

	@Override
	protected void onPlug(PluginableContainer c) {
		if (waitPlug != null) {
			waitPlug.toMet();
		}
	}

	@Override
	protected void onUnplug(PluginableContainer c) {
		clearAudio();
		waitPlug = new BlockDownCounter(1);
	}

	@Override
	public void clearAudio() throws ContainerClosedException {
		audios.clear();
	}

}
