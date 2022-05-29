package cn.milai.ib.autoconfig.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import cn.milai.ib.autoconfig.AudioAutoConfig;

/**
 * 开启媒体自动配置
 * @author milai
 * @date 2021.08.12
 */
@Import({ AudioAutoConfig.class })
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableMediaAutoConfig {

}
