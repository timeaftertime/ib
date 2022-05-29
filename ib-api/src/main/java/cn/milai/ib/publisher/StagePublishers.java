package cn.milai.ib.publisher;

import cn.milai.ib.stage.Stage;
import cn.milai.ib.stage.event.AddActorEvent;
import cn.milai.ib.stage.event.RemoveActorEvent;
import cn.milai.ib.stage.event.ResizedEvent;
import cn.milai.ib.stage.event.StageClosedEvent;
import cn.milai.ib.stage.event.StageRefreshedEvent;
import cn.milai.ib.stage.event.StageResetedEvent;
import cn.milai.ib.stage.event.StageStartedEvent;

/**
 * {@link Stage} 的 {@link Publisher}
 * @author milai
 * @date 2022.05.15
 */
public interface StagePublishers {

	/**
	 * {@link StageStartedEvent} 监听器
	 * @return
	 */
	Publisher<StageStartedEvent> onStarted();

	/**
	 * {@link StageRefreshedEvent} 监听器
	 * @return
	 */
	Publisher<StageRefreshedEvent> onRefreshed();

	/**
	 * {@link StageResetedEvent} 监听器
	 * @return
	 */
	Publisher<StageResetedEvent> onReseted();

	/**
	 * {@link StageClosedEvent} 监听器
	 * @return
	 */
	Publisher<StageClosedEvent> onClosed();

	/**
	 * 获取 {@link AddActorEvent} 监听器
	 * @return
	 */
	Publisher<AddActorEvent> onAddActor();

	/**
	 * 获取 {@link RemoveActorEvent} 监听器
	 * @return
	 */
	Publisher<RemoveActorEvent> onRemoveActor();

	/**
	 * 获取 {@link ResizedEvent} 监听器
	 * @return
	 */
	Publisher<ResizedEvent> onResized();
}
