package cn.milai.ib.component.form;

import cn.milai.ib.obj.IBComponent;
import cn.milai.ib.obj.Paintable;

/**
 * 用于窗体容器交互的组件，如按钮、提示等
 * 2019.11.30
 * @author milai
 */
public interface FormComponent extends IBComponent {

	@Override
	default int getPaintLayer() {
		return Paintable.GAME_COMPONENT_LAYER;
	}

	FormContainer getContainer();

}
