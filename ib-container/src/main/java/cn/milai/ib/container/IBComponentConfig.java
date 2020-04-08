package cn.milai.ib.container;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class IBComponentConfig {

	@Bean
	@Scope("singleton")
	@ConditionalOnMissingBean(AudioCreator.class)
	public AudioCreator defaultAudioCreator() {
		return new BaseAudioCreator();
	}
}
