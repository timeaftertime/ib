package cn.milai.ib.stage.event;

import cn.milai.ib.stage.Stage;

/**
 * {@link Stage} 重置事件
 * @author milai
 * @date 2022.05.19
 */
public class StageResetedEvent extends StageEvent {

	private int epoch;

	public StageResetedEvent(Stage stage, int epoch) {
		super(stage);
		this.epoch = epoch;
	}

	public int epoch() {
		return epoch;
	}

}
