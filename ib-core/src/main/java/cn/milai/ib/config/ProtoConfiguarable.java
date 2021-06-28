package cn.milai.ib.config;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * 标记一个类的属性可配置且为 {@link ConfigurableBeanFactory#SCOPE_PROTOTYPE} Bean
 * @author milai
 * @date 2021.06.12
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Configurable
public @interface ProtoConfiguarable {

}
