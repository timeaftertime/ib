package cn.milai.ib.container.autoconfig;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import cn.milai.ib.container.plugin.media.AudioCreator;
import cn.milai.ib.container.plugin.media.JLayerAudioCreator;
import cn.milai.ib.container.plugin.media.BaseMediaPlugin;
import cn.milai.ib.container.plugin.media.MediaPlugin;

/**
 * 媒体相关自动装载类
 * @author milai
 * @date 2021.08.12
 */
public class MediaAutoConfig {

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	@ConditionalOnMissingBean(AudioCreator.class)
	public AudioCreator defaultAudioCreator() {
		return new JLayerAudioCreator();
	}

	@Bean
	@ConditionalOnMissingBean(MediaPlugin.class)
	public MediaPlugin baseMediaPlugin() {
		return new BaseMediaPlugin();
	}
}
