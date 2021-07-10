package cn.milai.ib.item.property;

import java.util.concurrent.atomic.AtomicBoolean;

import cn.milai.ib.ex.ReinitializeException;
import cn.milai.ib.item.Item;

/**
 * {@link Property} 默认实现
 * @author milai
 * @date 2021.03.28
 */
public class BaseProperty implements Property {

	private Item o;
	private AtomicBoolean initialized = new AtomicBoolean();

	@Override
	public final void init(Item o) {
		if (!initialized.compareAndSet(false, true)) {
			throw new ReinitializeException(this);
		}
		this.o = o;
		initProperty();
	}

	/**
	 * 实际初始化，在 {@link #init(Item)} 后调用
	 */
	protected void initProperty() {}

	@Override
	public Item owner() {
		return o;
	}

}
