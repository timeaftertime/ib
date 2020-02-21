package cn.milai.ib.component.form;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import cn.milai.ib.container.AudioCreator;

@Configuration
public class IBComponentConfig {

	@Bean
	@Scope("singleton")
	@ConditionalOnMissingBean(AudioCreator.class)
	public AudioCreator defaultAudioCreator() {
		return new DefaultAudioCreator();
	}
}
