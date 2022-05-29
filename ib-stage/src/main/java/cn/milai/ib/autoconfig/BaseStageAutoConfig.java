package cn.milai.ib.autoconfig;

import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import cn.milai.ib.Roster;
import cn.milai.ib.plugin.Crew;
import cn.milai.ib.plugin.actor.AliveCheckCrew;
import cn.milai.ib.plugin.actor.BaseAliveCheckCrew;
import cn.milai.ib.plugin.actor.BaseDresserCrew;
import cn.milai.ib.plugin.actor.BaseExplosibleCrew;
import cn.milai.ib.plugin.actor.DresserCrew;
import cn.milai.ib.plugin.actor.ExplosibleCrew;
import cn.milai.ib.plugin.control.BaseControlCrew;
import cn.milai.ib.plugin.control.ControlCrew;
import cn.milai.ib.plugin.physics.BasePhysicsCrew;
import cn.milai.ib.plugin.physics.PhysicsCrew;
import cn.milai.ib.stage.BaseStage;
import cn.milai.ib.stage.Stage;

/**
 * 默认 {@link Stage} 配置
 * @author milai
 * @date 2021.05.15
 */
@Configuration
public class BaseStageAutoConfig {

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@ConditionalOnMissingBean(Roster.class)
	public Stage stage(List<Crew> crews) {
		Stage stage = new BaseStage();
		for (Crew crew : crews) {
			stage.addCrew(crew);
		}
		stage.onClosed().subscribe(e -> crews.forEach(Crew::destroy));
		return stage;
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@ConditionalOnMissingBean(ControlCrew.class)
	public ControlCrew baseControlCrew() {
		return new BaseControlCrew();
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@ConditionalOnMissingBean(PhysicsCrew.class)
	public PhysicsCrew basePhysicsCrew() {
		return new BasePhysicsCrew();
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@ConditionalOnMissingBean(AliveCheckCrew.class)
	public AliveCheckCrew baseAliveCheckCrew() {
		return new BaseAliveCheckCrew();
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@ConditionalOnMissingBean(ExplosibleCrew.class)
	public ExplosibleCrew baseExplosibleCrew() {
		return new BaseExplosibleCrew();
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@ConditionalOnMissingBean(DresserCrew.class)
	public DresserCrew baseDresserCrew() {
		return new BaseDresserCrew();
	}

}
