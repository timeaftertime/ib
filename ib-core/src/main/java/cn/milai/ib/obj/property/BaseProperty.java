package cn.milai.ib.obj.property;

import java.util.concurrent.atomic.AtomicBoolean;

import cn.milai.ib.obj.IBObject;

/**
 * {@link Property} 默认实现
 * @author milai
 * @date 2021.03.28
 */
public class BaseProperty implements Property {

	private IBObject o;
	private AtomicBoolean initialized = new AtomicBoolean();

	@Override
	public final void init(IBObject o) {
		if (!initialized.compareAndSet(false, true)) {
			throw new IllegalStateException("Property 不能重复初始化");
		}
		this.o = o;
		initProperty();
	}

	/**
	 * 实际初始化，在 {@link #init(IBObject)} 后调用
	 */
	protected void initProperty() {}

	@Override
	public IBObject owner() {
		return o;
	}

}
