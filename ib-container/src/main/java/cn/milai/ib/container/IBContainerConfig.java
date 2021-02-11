package cn.milai.ib.container;

import java.awt.image.BufferedImage;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import cn.milai.ib.container.plugin.media.AudioCreator;
import cn.milai.ib.container.plugin.media.BaseAudioCreator;
import cn.milai.ib.container.plugin.ui.BaseImage;
import cn.milai.ib.container.plugin.ui.Image;

/**
 * Spring Boot Starter 类
 * @author milai
 */
@Configuration
public class IBContainerConfig {

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	@ConditionalOnMissingBean(AudioCreator.class)
	public AudioCreator defaultAudioCreator() {
		return new BaseAudioCreator();
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@ConditionalOnMissingBean(Image.class)
	public Image defaultImage(BufferedImage... images) {
		return new BaseImage(images);
	}
}
