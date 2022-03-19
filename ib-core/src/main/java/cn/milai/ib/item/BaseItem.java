package cn.milai.ib.item;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import cn.milai.ib.config.Configurable;
import cn.milai.ib.container.Container;
import cn.milai.ib.ex.ReinitializeException;
import cn.milai.ib.geometry.BaseBounds;
import cn.milai.ib.geometry.Bounds;
import cn.milai.ib.geometry.slot.BoundsSlot;
import cn.milai.ib.item.property.Property;

/**
 * {@link Item} 默认实现
 * @author milai
 */
public class BaseItem implements Item, BoundsSlot {

	private static final String PROPERTY_NAME_FIELD = "NAME";

	private Bounds Bounds = new BaseBounds();
	private Container container;
	private String status = Item.STATUS_DEFAULT;
	private AtomicBoolean initialized = new AtomicBoolean();

	private Map<String, Property> properties = new ConcurrentHashMap<>();

	protected void ensureIn(double minX, double maxX, double minY, double maxY) {
		if (getX() < minX) {
			setX(minX);
		}
		if (getY() < minY) {
			setY(minY);
		}
		if (getX() + getW() > maxX) {
			setX(maxX - getW());
		}
		if (getY() + getH() > maxY) {
			setY(maxY - getH());
		}
	}

	@Override
	public void setStatus(String status) { this.status = status; }

	@Override
	public String getStatus() { return status; }

	@Override
	public final void init(Container container) {
		if (!initialized.compareAndSet(false, true)) {
			if (container == container()) {
				return;
			}
			throw new ReinitializeException(this);
		}
		this.container = container;
		initItem();
		for (Property p : properties.values()) {
			p.init(this);
		}
	}

	@Override
	public Container container() {
		return container;
	}

	/**
	 * 是否已经通过 {@link #init(Container)} 初始化
	 * @return
	 */
	protected boolean isInitialized() { return initialized.get(); }

	protected void initItem() {
	}

	@Override
	public <T extends Property> void putProperty(Class<T> clazz, Property p) {
		properties.put(propertyName(clazz), p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Property> T getProperty(Class<T> clazz) {
		return (T) properties.get(propertyName(clazz));
	}

	@Override
	public Map<String, Property> properties() {
		return Collections.unmodifiableMap(properties);
	}

	private String propertyName(Class<?> clazz) {
		String name = getPropertyName(clazz);
		if (name == null) {
			throw new IllegalArgumentException(clazz.getName() + " 未声明 " + PROPERTY_NAME_FIELD);
		}
		return name;
	}

	private String getPropertyName(Class<?> clazz) {
		try {
			return (String) clazz.getField(PROPERTY_NAME_FIELD).get(clazz);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			return null;
		}
	}

	@Override
	public int getZ() { return Layers.DEFAULT.getZ(); }

	@Override
	public Bounds bounds() {
		return Bounds;
	}

	@Override
	@Configurable
	public void setW(double w) {
		BoundsSlot.super.setW(w);
	}

	@Override
	@Configurable
	public void setH(double h) {
		BoundsSlot.super.setH(h);
	}

}
