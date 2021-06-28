package cn.milai.ib.control.text;

import cn.milai.ib.control.BaseControl;
import cn.milai.ib.control.property.base.BaseTextProperty;

/**
 * {@link TextControl} 抽象实现
 * @author milai
 * @date 2020.02.21
 */
public abstract class AbstractTextControl extends BaseControl implements TextControl {

	public AbstractTextControl() {
		setTextProperty(new BaseTextProperty());
	}

}
