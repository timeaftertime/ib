package cn.milai.ib.interaction.form;

import cn.milai.ib.component.IBComponent;
import cn.milai.ib.container.Container;
import cn.milai.ib.property.Paintable;

/**
 * 用于窗体容器交互的组件，如按钮、提示等
 * 2019.11.30
 * @author milai
 */
public abstract class IBFormComponent extends IBComponent implements Paintable {

	public IBFormComponent(int x, int y, Container container) {
		super(x, y, container);
	}

	@Override
	public int getPaintLayer() {
		return Paintable.GAME_COMPONENT_LAYER;
	}

	@Override
	public FormContainer getContainer() {
		return (FormContainer) super.getContainer();
	}

}
