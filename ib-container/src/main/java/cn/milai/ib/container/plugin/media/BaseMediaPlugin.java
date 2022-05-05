package cn.milai.ib.container.plugin.media;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import cn.milai.ib.container.ContainerClosedException;
import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.plugin.BaseExclusiveContainerPlugin;
import cn.milai.ib.container.pluginable.PluginableContainer;

/**
 * {@link MediaPlugin} 默认实现
 * @author milai
 * @date 2021.02.09
 */
public class BaseMediaPlugin extends BaseExclusiveContainerPlugin implements MediaPlugin {

	private Map<String, Audio> audios = new ConcurrentHashMap<>();
	private Set<String> fetched = new HashSet<>();

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

	@Override
	public void onRefresh(LifecycleContainer container) {
		if (container.isPaused()) {
			return;
		}
		for (String code : audios.keySet()) {
			Audio audio = audios.get(code);
			if (audio == null) {
				continue;
			}
			// 判断 contains 和 add 操作都在 Audio 主线程，不需要同步
			if (!fetched.contains(audio.getCode())) {
				fetched.add(audio.getCode());
				container().eventLoop().submit(() -> {
					audio.play();
					if (audio.isComplete()) {
						audios.remove(code, audio);
					}
					fetched.remove(audio.getCode());
				});
			}
		}
	}

	@Override
	public void onEpochChanged(LifecycleContainer container) {
		clearAudio();
	}

	@Override
	protected void onUnplug(PluginableContainer c) {
		clearAudio();
	}

	@Override
	public void clearAudio() throws ContainerClosedException {
		audios.clear();
	}

}
