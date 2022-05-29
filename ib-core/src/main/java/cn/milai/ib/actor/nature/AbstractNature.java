package cn.milai.ib.actor.nature;

import cn.milai.ib.actor.Actor;

/**
 * {@link Nature} 默认实现
 * @author milai
 * @date 2021.03.28
 */
public abstract class AbstractNature implements Nature {

	private Actor owner;

	public AbstractNature(Actor owner) {
		this.owner = owner;
	}

	@Override
	public Actor owner() {
		return owner;
	}

}
