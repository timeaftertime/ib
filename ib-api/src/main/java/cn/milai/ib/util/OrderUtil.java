package cn.milai.ib.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.milai.ib.annotation.Order;

public abstract class OrderUtil {

	private OrderUtil() {

	}

	private static <T> Comparator<T> getComparator() {
		return (o1, o2) -> {
			Order order1 = getOrder(o1);
			Order order2 = getOrder(o2);
			return order2.value() - order1.value();
		};
	}

	private static Order getOrder(Object o) {
		Order order = AnnotationUtil.findAnnotation(o.getClass(), Order.class);
		if (order == null) {
			throw new IllegalArgumentException(
					String.format("使用 Order 排序的类型必须添加 %s 注解，class = %s", Order.class.getName(), o.getClass().getName()));
		}
		return order;
	}

	/**
	 * 对所有元素都使用了 Order 注解的 list 进行排序
	 * @param list
	 * @throws IllegalArgumentException 若 list 存在元素的类型没有使用 Order 注解
	 */
	public static <T> void sort(List<T> list) {
		Collections.sort(list, getComparator());
	}
}
