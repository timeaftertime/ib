package cn.milai.ib.actor;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import cn.milai.ib.actor.config.Configurable;
import cn.milai.ib.actor.event.ActorEvent;
import cn.milai.ib.actor.nature.Nature;
import cn.milai.ib.geometry.BaseBounds;
import cn.milai.ib.geometry.Bounds;
import cn.milai.ib.geometry.slot.BoundsSlot;
import cn.milai.ib.lifecycle.Lifecycle;
import cn.milai.ib.publisher.OncePublisher;
import cn.milai.ib.publisher.Publisher;
import cn.milai.ib.stage.Stage;

/**
 * {@link Actor} 默认实现
 * @author milai
 */
public class BaseActor implements Actor, BoundsSlot {

	private Stage stage;
	private Bounds Bounds = new BaseBounds();
	private String status = Actor.STATUS_DEFAULT;

	private AtomicBoolean makeUp = new AtomicBoolean();
	private Publisher<ActorEvent> onMakeUp = new OncePublisher<>();

	private Map<String, Nature> natures = new ConcurrentHashMap<>();

	@Override
	public Actor makeup() {
		if (!makeUp.compareAndSet(false, true)) {
			return this;
		}
		onMakeUp.publish(new ActorEvent(this));
		return this;
	}

	@Override
	public boolean isMakeup() { return makeUp.get(); }

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Actor> T onMakeUp(Consumer<ActorEvent> listener) {
		onMakeUp.subscribe(listener);
		return (T) this;
	}

	@Override
	public void putNature(Nature p) {
		natures.put(p.name(), p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Nature> T getNature(String name) {
		return (T) natures.get(name);
	}

	@Override
	public Map<String, Nature> natures() {
		return Collections.unmodifiableMap(natures);
	}

	@Override
	public void setStatus(String status) { this.status = status; }

	@Override
	public String getStatus() { return status; }

	@Override
	public void enter(Stage stage) {
		if (stage == this.stage) {
			return;
		}
		Lifecycle lifecycle = stage.lifecycle();
		if (!lifecycle.inMainLoop()) {
			lifecycle.submit(() -> enter(stage));
			return;
		}
		// 先设置 stage，避免 addActor 导致 enter 重复执行
		this.stage = stage;
		stage.addActor(this);
		onEnterStage(stage);
	}

	/**
	 *{@link #enter(Stage)} 时调用
	 * @param stage
	 */
	protected void onEnterStage(Stage stage) {
	}

	@Override
	public void exit() {
		Stage stage = this.stage;
		if (stage == null) {
			return;
		}
		Lifecycle lifecycle = stage.lifecycle();
		if (!lifecycle.inMainLoop()) {
			lifecycle.submit(this::exit);
			return;
		}
		// 先设置 stage 为 null，避免 removeActor 导致 exit 重复执行
		this.stage = null;
		stage.removeActor(this);
		onExitStage(stage);
	}

	/**
	 * {@link #exit()} 时调用
	 * @param stage
	 */
	protected void onExitStage(Stage stage) {
	}

	@Override
	public Stage stage() {
		return stage;
	}

	@Override
	public Bounds bounds() {
		return Bounds;
	}

	@Override
	@Configurable
	public void setW(double w) {
		BoundsSlot.super.setW(w);
	}

	@Override
	@Configurable
	public void setH(double h) {
		BoundsSlot.super.setH(h);
	}

}
