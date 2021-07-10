package cn.milai.ib.item.property;

import cn.milai.ib.item.Item;

/**
 * {@link Item} 属性
 * @author milai
 * @date 2021.03.28
 */
public interface Property {

	/**
	 * 属性类别，子类必须定义该 public static 字段
	 */
	String NAME = null;

	/**
	 * 关联到指定 {@link Item} 进行初始化，重复初始化将抛出异常
	 * @param o
	 * @throws 若重复初始化
	 */
	void init(Item o) throws IllegalStateException;

	/**
	 * 获取当前 {@link Property} 关联的 {@link Item}
	 * @return
	 */
	Item owner();

}
