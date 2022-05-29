package cn.milai.ib;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import cn.milai.ib.actor.Actor;

/**
 * {@link Roster} 默认实现
 * @author milai
 * @date 2022.05.14
 */
public class BaseRoster<T> implements Roster<T> {

	private Set<T> targets = Collections.newSetFromMap(new ConcurrentHashMap<>());
	private Set<T> addLocked = Collections.newSetFromMap(new ConcurrentHashMap<>());
	private Set<T> removeLocked = Collections.newSetFromMap(new ConcurrentHashMap<>());

	@Override
	public boolean add(T target) {
		if (targets.add(target)) {
			addLocked.remove(target);
			return true;
		}
		return false;
	}

	@Override
	public boolean remove(T target) {
		if (targets.remove(target)) {
			removeLocked.remove(target);
			return true;
		}
		return false;
	}

	@Override
	public boolean addLock(T t) {
		return addLocked.add(t);
	}

	@Override
	public boolean addUnlock(T t) {
		return addLocked.remove(t);
	}

	@Override
	public boolean removeLock(T t) {
		return removeLocked.add(t);
	}

	@Override
	public boolean removeUnlock(T t) {
		return removeLocked.remove(t);
	}

	@Override
	public Set<T> getAll() { return targets; }

	@Override
	public Set<T> clear() {
		Set<T> res = new HashSet<>(this.targets);
		this.targets.removeAll(res);
		return res;
	}

	@Override
	public boolean contains(Actor actor) {
		return targets.contains(actor);
	}

}
