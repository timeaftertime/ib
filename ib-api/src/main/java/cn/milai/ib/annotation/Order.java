package cn.milai.ib.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 排序注解
 * 2020.01.16
 * @author milai
 */
@Inherited
@Retention(RUNTIME)
@Target(TYPE)
public @interface Order {

	int MAX_ORDER = Integer.MAX_VALUE;

	/**
	 * 排序优先级，越大越排在前面
	 * @return
	 */
	int value() default 0;
}
