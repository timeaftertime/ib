package cn.milai.ib.util;

import java.lang.annotation.Annotation;

/**
 * 注解工具类
 * 2020.01.16
 * @author milai
 */
public abstract class AnnotationUtil {

	private AnnotationUtil() {
		
	}
	
	/**
	 * 在 clazz 上寻找并返回指定类型的注解，若不存在，返回 null
	 * 暂不支持递归寻找
	 * @param clazz
	 * @param annotationType
	 * @return
	 */
	public static <A extends Annotation> A findAnnotation(Class<?> clazz, Class<A> annotationType) {
		return clazz.getDeclaredAnnotation(annotationType);
	}
}
