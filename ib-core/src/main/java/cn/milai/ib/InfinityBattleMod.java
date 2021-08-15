package cn.milai.ib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Infinity Battle 的模块。
 * 标注该注解的类在 {@link InfinityBattleApplication} 类所在目录下时，
 * 类中定义的 bean、类所在包及其子包的 Spring 组件将被扫描。
 * @author milai
 * @date 2021.01.17
 */
@Configuration
@ComponentScan
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface InfinityBattleMod {

}
