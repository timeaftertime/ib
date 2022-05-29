package cn.milai.ib.autoconfig;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import cn.milai.ib.plugin.actor.BaseDresserCrew;
import cn.milai.ib.plugin.actor.DresserCrew;

/**
 * {@link DresserCrew} 相关自动装载类
 * @author milai
 * @date 2022.05.24
 */
public class DresserAutoConfig {

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@ConditionalOnMissingBean(DresserCrew.class)
	public DresserCrew baseDresserCrew() {
		return new BaseDresserCrew();
	}
}
