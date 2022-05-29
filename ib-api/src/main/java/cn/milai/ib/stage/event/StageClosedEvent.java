package cn.milai.ib.stage.event;

import cn.milai.ib.stage.Stage;

/**
 * {@link Stage} 关闭事件
 * @author milai
 * @date 2022.05.19
 */
public class StageClosedEvent extends StageEvent {

	public StageClosedEvent(Stage stage) {
		super(stage);
	}

}
