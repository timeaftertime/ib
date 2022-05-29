package cn.milai.ib.stage.event;

import cn.milai.ib.stage.Stage;

/**
 * {@link Stage} 开始的事件 
 * @author milai
 * @date 2022.05.19
 */
public class StageStartedEvent extends StageEvent {

	public StageStartedEvent(Stage stage) {
		super(stage);
	}

}
