package cn.milai.ib.stage.event;

import cn.milai.ib.stage.Stage;

/**
 * {@link Stage#resize(double, double)} 事件
 * @author milai
 * @date 2022.05.15
 */
public class ResizedEvent extends StageEvent {

	public ResizedEvent(Stage stage) {
		super(stage);
	}

}
