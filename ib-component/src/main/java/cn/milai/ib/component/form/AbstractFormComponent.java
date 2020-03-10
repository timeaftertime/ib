package cn.milai.ib.component.form;

import java.awt.Image;

import cn.milai.ib.container.form.FormComponent;
import cn.milai.ib.container.form.FormContainer;
import cn.milai.ib.obj.AbstractIBObject;

/**
 * IBFormComponent 的抽象基类
 * @author milai
 * @date 2020.02.20
 */
public abstract class AbstractFormComponent extends AbstractIBObject implements FormComponent {

	public AbstractFormComponent(int x, int y, FormContainer container) {
		super(x, y, container);
	}

	@Override
	public FormContainer getContainer() {
		return (FormContainer) super.getContainer();
	}

	@Override
	public void setImage(Image img) {
		throw new UnsupportedOperationException();
	}

}
