package cn.milai.ib.stage.event;

import cn.milai.ib.publisher.Event;
import cn.milai.ib.stage.Stage;

/**
 * {@link Stage} 的 {@link Event}
 * @author milai
 * @date 2022.05.15
 */
public class StageEvent implements Event {

	private Stage stage;

	public StageEvent(Stage stage) {
		this.stage = stage;
	}

	public Stage stage() {
		return stage;
	}

}
