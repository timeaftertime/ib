package cn.milai.ib.stage.event;

import cn.milai.ib.stage.Stage;

/**
 * {@link Stage} 刷新事件
 * @author milai
 * @date 2022.05.19
 */
public class StageRefreshedEvent extends StageEvent {

	private long frame;

	public StageRefreshedEvent(Stage stage, long frame) {
		super(stage);
		this.frame = frame;
	}

	public long frame() {
		return frame;
	}
}
