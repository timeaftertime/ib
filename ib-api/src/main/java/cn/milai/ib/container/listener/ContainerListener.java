package cn.milai.ib.container.listener;

import cn.milai.ib.container.Container;
import cn.milai.ib.container.lifecycle.LifecycleContainer;

/**
 * {@link Container} 监听器
 * @author milai
 * @date 2021.02.17
 */
public interface ContainerListener {

	/**
	 * 是否只存在于一个纪元，即 {@link LifecycleContainer#reset()} 时是否移除当前监听器
	 * @return
	 */
	default boolean inEpoch() {
		return false;
	}

}
