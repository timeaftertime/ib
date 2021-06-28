package cn.milai.ib.control.property.holder;

import cn.milai.ib.control.property.TextProperty;
import cn.milai.ib.obj.IBObject;

/**
 * {@link TextProperty} 持有者
 * @author milai
 * @date 2021.06.26
 */
public interface TextPropertyHolder extends IBObject {

	/**
	 * 获取持有的 {@link TextProperty}
	 * @return
	 */
	default TextProperty getTextProperty() { return getProperty(TextProperty.class); }

	/**
	 * 设置关联的 {@link TextProperty}
	 * @param p
	 */
	default void setTextProperty(TextProperty p) {
		putProperty(TextProperty.class, p);
	}

}
