package cn.milai.ib;

import java.util.Set;

import cn.milai.ib.actor.Actor;

/**
 * 名单，元素不重复的集合
 * @author milai
 * @date 2022.05.14
 */
public interface Roster<T> {

	/**
	 * 添加并 {@link #addLock(Object)} 一个元素
	 * @param t
	 * @return 是否实际添加
	 */
	boolean add(T t);

	/**
	 * 移除并 {@link #removeLock(Object)} 一个元素
	 * @param t
	 * @return 是否实际移除
	 */
	boolean remove(T t);

	/**
	 * 锁定一个元素
	 * @param t
	 * @return
	 */
	boolean addLock(T t);

	/**
	 * 解锁一个元素
	 * @param t
	 * @return
	 */
	boolean addUnlock(T t);

	/**
	 * 锁定一个待移除元素
	 * @param t
	 * @return
	 */
	boolean removeLock(T t);

	/**
	 * 解锁一个待移除元素
	 * @param t
	 * @return
	 */
	boolean removeUnlock(T t);

	/**
	 * 获取已经添加的对象列表
	 * @return
	 */
	Set<T> getAll();

	/**
	 * 清空列表并返回所有被移除的对象
	 */
	Set<T> clear();

	/**
	 * 是否包含指定对象
	 * @param actor
	 * @return
	 */
	boolean contains(Actor actor);

}
