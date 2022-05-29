package cn.milai.ib.plugin;

import java.util.function.Consumer;

import cn.milai.ib.stage.event.AddActorEvent;
import cn.milai.ib.stage.event.RemoveActorEvent;
import cn.milai.ib.stage.event.ResizedEvent;
import cn.milai.ib.stage.event.StageClosedEvent;
import cn.milai.ib.stage.event.StageRefreshedEvent;
import cn.milai.ib.stage.event.StageResetedEvent;
import cn.milai.ib.stage.event.StageStartedEvent;

/**
 * {@link Crew} 抽象实现
 * @author milai
 * @date 2022.05.15
 */
public abstract class AbstractCrew extends AbstractPlugin implements Crew {

	private Consumer<StageStartedEvent> onStarted;
	private Consumer<StageRefreshedEvent> onRefreshed;
	private Consumer<StageResetedEvent> onReseted;
	private Consumer<StageClosedEvent> onClosed;
	private Consumer<AddActorEvent> onAddActor;
	private Consumer<RemoveActorEvent> onRemoveActor;
	private Consumer<ResizedEvent> onResized;

	@Override
	public final Consumer<StageStartedEvent> onStarted() {
		if (onStarted == null) {
			onStarted = createOnStarted();
		}
		return onStarted;
	}

	protected Consumer<StageStartedEvent> createOnStarted() {
		return null;
	}

	@Override
	public final Consumer<StageRefreshedEvent> onRefreshed() {
		if (onRefreshed == null) {
			onRefreshed = createOnRefreshed();
		}
		return onRefreshed;
	}

	protected Consumer<StageRefreshedEvent> createOnRefreshed() {
		return null;
	}

	@Override
	public final Consumer<StageResetedEvent> onReseted() {
		if (onReseted == null) {
			onReseted = createOnReseted();
		}
		return onReseted;
	}

	protected Consumer<StageResetedEvent> createOnReseted() {
		return null;
	}

	@Override
	public final Consumer<StageClosedEvent> onClosed() {
		if (onClosed == null) {
			onClosed = createOnClosed();
		}
		return onClosed;
	}

	protected Consumer<StageClosedEvent> createOnClosed() {
		return null;
	}

	@Override
	public final Consumer<AddActorEvent> onAddActor() {
		if (onAddActor == null) {
			onAddActor = createOnAddActor();
		}
		return onAddActor;
	}

	protected Consumer<AddActorEvent> createOnAddActor() {
		return null;
	}

	@Override
	public final Consumer<RemoveActorEvent> onRemoveActor() {
		if (onRemoveActor == null) {
			onRemoveActor = createOnRemoveActor();
		}
		return onRemoveActor;
	}

	protected Consumer<RemoveActorEvent> createOnRemoveActor() {
		return null;
	}

	@Override
	public final Consumer<ResizedEvent> onResized() {
		if (onResized == null) {
			onResized = createOnResized();
		}
		return onResized;
	}

	protected Consumer<ResizedEvent> createOnResized() {
		return null;
	}

}
