package cn.milai.ib.component.form;

import java.awt.Image;

import cn.milai.ib.container.Container;
import cn.milai.ib.container.form.FormComponent;
import cn.milai.ib.container.form.FormContainer;
import cn.milai.ib.obj.AbstractIBObject;

/**
 * IBFormComponent 的抽象基类
 * @author milai
 * @date 2020.02.20
 */
public abstract class AbstractFormComponent extends AbstractIBObject implements FormComponent {

	public AbstractFormComponent(int x, int y, Container container) {
		super(x, y, castToFormContainer(container));
	}

	/**
	 * 强转以确保只能接受 FormContainer
	 * @param container
	 * @return
	 */
	private static FormContainer castToFormContainer(Container container) {
		return (FormContainer) container;
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
