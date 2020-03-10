package cn.milai.ib.container.form;

import cn.milai.ib.obj.IBComponent;

/**
 * 用于窗体容器交互的组件，如按钮、提示等
 * 2019.11.30
 * @author milai
 */
public interface FormComponent extends IBComponent {

	@Override
	FormContainer getContainer();

}
