package cn.milai.ib.autoconfig;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import cn.milai.ib.plugin.audio.Audio;
import cn.milai.ib.plugin.audio.AudioCreator;
import cn.milai.ib.plugin.audio.AudioCrew;
import cn.milai.ib.plugin.audio.BaseAudioCrew;
import cn.milai.ib.plugin.audio.JLayerAudioCreator;

/**
 *  {@link Audio} 相关自动装载类
 * @author milai
 * @date 2021.08.12
 */
public class AudioAutoConfig {

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	@ConditionalOnMissingBean(AudioCreator.class)
	public AudioCreator defaultAudioCreator() {
		return new JLayerAudioCreator();
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@ConditionalOnMissingBean(AudioCrew.class)
	public AudioCrew baseAudioCrew() {
		return new BaseAudioCrew();
	}
}
