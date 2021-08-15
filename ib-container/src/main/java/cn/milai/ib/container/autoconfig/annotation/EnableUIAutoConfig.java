package cn.milai.ib.container.autoconfig.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import cn.milai.ib.container.autoconfig.UIAutoConfig;

/**
 * 开启 UI 自动配置
 * @author milai
 * @date 2021.08.12
 */
@Import({ UIAutoConfig.class })
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableUIAutoConfig {

}
