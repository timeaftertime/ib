package cn.milai.ib.stage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import cn.milai.common.collection.Creator;
import cn.milai.ib.BaseRoster;
import cn.milai.ib.Roster;
import cn.milai.ib.actor.Actor;
import cn.milai.ib.actor.nature.Nature;
import cn.milai.ib.ex.IBException;
import cn.milai.ib.geometry.BaseBounds;
import cn.milai.ib.geometry.Bounds;
import cn.milai.ib.lifecycle.Lifecycle;
import cn.milai.ib.lifecycle.LifecycleListener;
import cn.milai.ib.plugin.Crew;
import cn.milai.ib.publisher.BasePublisher;
import cn.milai.ib.publisher.OncePublisher;
import cn.milai.ib.publisher.Publisher;
import cn.milai.ib.publisher.RecordPublisher;
import cn.milai.ib.stage.event.AddActorEvent;
import cn.milai.ib.stage.event.RemoveActorEvent;
import cn.milai.ib.stage.event.ResizedEvent;
import cn.milai.ib.stage.event.StageClosedEvent;
import cn.milai.ib.stage.event.StageRefreshedEvent;
import cn.milai.ib.stage.event.StageResetedEvent;
import cn.milai.ib.stage.event.StageStartedEvent;

/**
 * {@link Stage} 的默认实现
 * @author milai
 * @date 2020.12.05
 */
@SuppressWarnings("unchecked")
public class BaseStage implements Stage {

	private boolean paused = false;
	private volatile boolean pined = false;

	private Bounds bounds = new BaseBounds();
	private Lifecycle lifecycle = new BaseLifecycle(new StageLifecycleListener());
	private Roster<Actor> actors = new BaseRoster<>();
	private Roster<Crew> crews = new BaseRoster<>();

	private Publisher<StageStartedEvent> onStarted = new OncePublisher<>();
	private Publisher<StageRefreshedEvent> onRefreshed = new BasePublisher<>();
	private Publisher<StageResetedEvent> onReseted = new BasePublisher<>();
	private Publisher<StageClosedEvent> onClosed = new OncePublisher<>();
	private Publisher<AddActorEvent> onAddActor = new BasePublisher<>();
	private Publisher<RemoveActorEvent> onRemoveActor = new BasePublisher<>();
	private Publisher<ResizedEvent> onResized = new RecordPublisher<>();

	private java.util.concurrent.Future<?> addActorAsync(Actor actor) {
		if (!lifecycle().inMainLoop()) {
			return lifecycle().submit(() -> addActor(actor));
		}
		if (!actors.addLock(actor) || containsActor(actor)) {
			actors.addUnlock(actor);
			return CompletableFuture.completedFuture(null);
		}
		onAddActor.publish(new AddActorEvent(this, actor));
		actors.add(actor);
		actor.enter(this);
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public Stage addActor(Actor actor) {
		addActorAsync(actor);
		return this;
	}

	@Override
	public Stage addActorSync(Actor actor) {
		try {
			addActorAsync(actor).get();
			return this;
		} catch (InterruptedException | ExecutionException e) {
			throw new IBException(e);
		}
	}

	private java.util.concurrent.Future<?> removeActorAsync(Actor actor) {
		if (!lifecycle().inMainLoop()) {
			return lifecycle().submit(() -> removeActor(actor));
		}
		if (!actors.removeLock(actor) || !containsActor(actor)) {
			actors.removeUnlock(actor);
			return CompletableFuture.completedFuture(null);
		}
		onRemoveActor.publish(new RemoveActorEvent(this, Creator.asSet(actor)));
		actors.remove(actor);
		actor.exit();
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public Stage removeActor(Actor actor) {
		removeActorAsync(actor);
		return this;
	}

	@Override
	public Stage removeActorSync(Actor actor) {
		try {
			removeActorAsync(actor).get();
			return this;
		} catch (InterruptedException | ExecutionException e) {
			throw new IBException(e);
		}
	}

	private Future<?> clearActorAsync() {
		if (!lifecycle().inMainLoop()) {
			return lifecycle().submit(this::clearActor);
		}
		Set<Actor> removed = actors.clear();
		if (!removed.isEmpty()) {
			removed.forEach(Actor::exit);
			onRemoveActor.publish(new RemoveActorEvent(this, removed));
		}
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public Stage clearActor() {
		clearActorAsync();
		return this;
	}

	@Override
	public Stage clearActorSync() {
		try {
			clearActorAsync().get();
			return this;
		} catch (InterruptedException | ExecutionException e) {
			throw new IBException(e);
		}
	}

	@Override
	public boolean containsActor(Actor actor) {
		return actors.contains(actor);
	}

	@Override
	public <T extends Actor> Set<T> getAll(Class<T> type) {
		if (type == Actor.class) {
			return (Set<T>) actors.getAll();
		}
		return getAll(actors, type);
	}

	@Override
	public Set<Actor> getAll() { return getAll(Actor.class); }

	@Override
	public <T extends Nature> List<T> getNatures(String name) {
		List<T> natures = new ArrayList<>();
		for (Actor actor : actors.getAll()) {
			Nature nature = actor.getNature(name);
			if (nature != null) {
				natures.add((T) nature);
			}
		}
		return natures;
	}

	@Override
	public Stage addCrew(Crew crew) {
		if (!crew.plug(this)) {
			return this;
		}
		crews.add(crew);
		subscribe(crew);
		return this;
	}

	@Override
	public Stage removeCrew(Crew crew) {
		crew.unplug(this);
		crews.remove(crew);
		unsubscribe(crew);
		return this;
	}

	private void subscribe(Crew crew) {
		acceptListener(crew.onStarted(), onStarted::subscribe);
		acceptListener(crew.onRefreshed(), onRefreshed::subscribe);
		acceptListener(crew.onReseted(), onReseted::subscribe);
		acceptListener(crew.onClosed(), onClosed::subscribe);
		acceptListener(crew.onAddActor(), onAddActor::subscribe);
		acceptListener(crew.onRemoveActor(), onRemoveActor::subscribe);
		acceptListener(crew.onResized(), onResized::subscribe);
	}

	private void unsubscribe(Crew crew) {
		acceptListener(crew.onStarted(), onStarted::unsubscribe);
		acceptListener(crew.onRefreshed(), onRefreshed::unsubscribe);
		acceptListener(crew.onReseted(), onReseted::unsubscribe);
		acceptListener(crew.onClosed(), onClosed::unsubscribe);
		acceptListener(crew.onAddActor(), onAddActor::unsubscribe);
		acceptListener(crew.onRemoveActor(), onRemoveActor::unsubscribe);
		acceptListener(crew.onResized(), onResized::unsubscribe);
	}

	private <T> void acceptListener(Consumer<T> listener, Consumer<Consumer<T>> accepter) {
		if (listener == null) {
			return;
		}
		accepter.accept(listener);
	}

	@Override
	public <C extends Crew> Set<C> getPlugins(Class<C> crewClass) {
		return getAll(crews, crewClass);
	}

	private static <C extends T, T> Set<C> getAll(Roster<T> roster, Class<C> type) {
		Set<C> targets = new HashSet<>();
		for (T r : roster.getAll()) {
			if (type.isInstance(r)) {
				targets.add((C) r);
			}
		}
		return targets;
	}

	@Override
	public final void switchPause() {
		paused = !paused;
	}

	@Override
	public final void setPined(boolean pined) { this.pined = pined; }

	@Override
	public boolean isPaused() { return paused; }

	@Override
	public boolean isPined() { return pined; }

	@Override
	public Lifecycle lifecycle() {
		return lifecycle;
	}

	@Override
	public Bounds bounds() {
		return bounds;
	}

	@Override
	public Publisher<StageStartedEvent> onStarted() {
		return onStarted;
	}

	@Override
	public Publisher<StageRefreshedEvent> onRefreshed() {
		return onRefreshed;
	}

	@Override
	public Publisher<StageResetedEvent> onReseted() {
		return onReseted;
	}

	@Override
	public Publisher<StageClosedEvent> onClosed() {
		return onClosed;
	}

	@Override
	public Publisher<AddActorEvent> onAddActor() {
		return onAddActor;
	};

	@Override
	public Publisher<RemoveActorEvent> onRemoveActor() {
		return onRemoveActor;
	};

	@Override
	public Publisher<ResizedEvent> onResized() {
		return onResized;
	};

	private class StageLifecycleListener implements LifecycleListener {
		@Override
		public void onStarted(Lifecycle lifecycle) {
			onStarted.publish(new StageStartedEvent(BaseStage.this));
		}

		@Override
		public void onRefreshed(Lifecycle lifecycle, long frame) {
			onRefreshed.publish(new StageRefreshedEvent(BaseStage.this, frame));
		}

		@Override
		public void onReseted(Lifecycle lifecycle, int epoch) {
			onReseted.publish(new StageResetedEvent(BaseStage.this, epoch));
		}

		@Override
		public void onClosed(Lifecycle lifecycle) {
			onClosed.publish(new StageClosedEvent(BaseStage.this));
		}
	}
}
