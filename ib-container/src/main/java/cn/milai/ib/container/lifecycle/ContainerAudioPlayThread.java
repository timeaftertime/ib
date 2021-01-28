package cn.milai.ib.container.lifecycle;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import cn.milai.ib.container.Container;
import cn.milai.ib.container.ui.Audio;
import cn.milai.ib.util.WaitUtil;

/**
 * 容器音乐播放线程
 * @author milai
 * @date 2020.03.25
 */
public class ContainerAudioPlayThread extends Thread {

	private static final String THREAD_NAME = "IBAudioPlay";
	private static final int THREAD_POOL_SIZE = 2;

	private AbstractLifecycleContainer container;
	private Map<String, Audio> audios;
	private ExecutorService pool;

	/**
	 * 已经被某个线程服务的音频
	 */
	private List<String> fetched;

	ContainerAudioPlayThread(Container container) {
		this.container = (AbstractLifecycleContainer) container;
		audios = Maps.newConcurrentMap();
		fetched = Lists.newArrayList();
		pool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
	}

	@Override
	public void run() {
		setName(THREAD_NAME);
		while (!container.isClosed()) {
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
			WaitUtil.wait(container, 1L);
		}
		pool.shutdown();
	}

	/**
	 * 添加一个待播放音频
	 * @param audio
	 */
	void addAudio(Audio audio) {
		audios.put(audio.getCode(), audio);
	}

	/**
	 * 停止一个音频的播放
	 * @param code
	 */
	void removeAudio(String code) {
		audios.remove(code);
	}

	/**
	 * 清楚所有待播放的音乐
	 */
	void reset() {
		for (String code : audios.keySet()) {
			audios.remove(code);
		}
	}
}
