package cn.milai.ib.plugin.audio;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import cn.milai.ib.plugin.BaseExclusiveCrew;
import cn.milai.ib.stage.Stage;
import cn.milai.ib.stage.event.StageRefreshedEvent;
import cn.milai.ib.stage.event.StageResetedEvent;

/**
 * {@link AudioCrew} 默认实现
 * @author milai
 * @date 2021.02.09
 */
public class BaseAudioCrew extends BaseExclusiveCrew implements AudioCrew {

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
	public Consumer<StageRefreshedEvent> createOnRefreshed() {
		return e -> {
			Stage stage = e.stage();
			if (stage.isPaused()) {
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
					stage.lifecycle().loop().submitDeputy(() -> {
						audio.play();
						if (audio.isComplete()) {
							audios.remove(code, audio);
						}
						fetched.remove(audio.getCode());
					});
				}
			}
		};
	}

	@Override
	public Consumer<StageResetedEvent> createOnReseted() {
		return e -> clearAudio();
	}

	@Override
	protected void onUnplug(Stage s) {
		clearAudio();
	}

	@Override
	public void clearAudio() {
		audios.clear();
	}

}
