package cn.milai.ib.stage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

import cn.milai.common.thread.counter.BlockDownCounter;
import cn.milai.common.thread.counter.Counter;
import cn.milai.ib.actor.Actor;
import cn.milai.ib.plugin.BaseExclusiveCrew;
import cn.milai.ib.stage.event.RemoveActorEvent;
import cn.milai.ib.stage.event.StageClosedEvent;
import cn.milai.ib.stage.event.StageRefreshedEvent;
import cn.milai.ib.stage.event.StageResetedEvent;

/**
 * 等待同步相关工具类
 * @author milai
 */
public class Waits {

	private Waits() {
	}

	private static Map<Thread, Counter> registered = new ConcurrentHashMap<>();

	/**
	 * 使当前线程等待 {@link Stage} 经过指定帧数或被关闭或重置
	 * 返回前将清除中断状态
	 * @param stage
	 * @param frame 等待的帧数
	 */
	public static void wait(Stage stage, long frame) {
		Counter counter = registerCounter(frame);
		doWait(stage, new RefreshCounter(counter), null);
	}

	/**
	 * 使当前线程等待到 actor 被从所属 {@link Stage} 移除或 {@link Stage} 被关闭。
	 * 若目标 {@link Actor} 不在 {@link Stage} 中，将立刻返回。
	 * 返回前将清除中断状态
	 * @param stage
	 * @param actor
	 */
	public static void waitRemove(Actor actor) {
		Stage stage = actor.stage();
		if (stage == null) {
			return;
		}
		Counter counter = registerCounter(1);
		doWait(stage, new RemoveCounter(counter, actor), () -> !stage.containsActor(actor));
	}

	private static Counter registerCounter(long cnt) {
		Counter counter = new BlockDownCounter(cnt);
		registered.put(Thread.currentThread(), counter);
		return counter;
	}

	/**
	 * 若指定线程正通过 {@link Waits} 等待某个 {@link Counter}，停止其等待
	 * @param thread
	 */
	public static void notify(Thread thread) {
		Counter counter = registered.get(thread);
		if (counter != null) {
			registered.remove(thread);
			counter.toMet();
		}
	}

	private static void doWait(Stage stage, ResetMonitor monitor, Supplier<Boolean> directReturn) {
		stage.addCrew(monitor);
		if (directReturn == null || !directReturn.get()) {
			monitor.getCounter().await();
		}
		stage.removeCrew(monitor);
	}

	private static class ResetMonitor extends BaseExclusiveCrew {

		private Counter c;

		public ResetMonitor(Counter c) {
			this.c = c;
		}

		public Counter getCounter() { return c; }

		@Override
		protected Consumer<StageClosedEvent> createOnClosed() {
			return e -> c.toMet();
		}

		@Override
		protected Consumer<StageResetedEvent> createOnReseted() {
			return e -> c.toMet();
		}

	}

	private static class RefreshCounter extends ResetMonitor {

		public RefreshCounter(Counter c) {
			super(c);
		}

		@Override
		protected Consumer<StageRefreshedEvent> createOnRefreshed() {
			return e -> getCounter().count();
		}

	}

	private static class RemoveCounter extends ResetMonitor {
		private Actor target;

		public RemoveCounter(Counter counter, Actor target) {
			super(counter);
			this.target = target;
		}

		@Override
		protected Consumer<RemoveActorEvent> createOnRemoveActor() {
			return e -> {
				for (Actor obj : e.actors()) {
					if (this.target == obj) {
						getCounter().count();
					}
				}
			};
		}

	}

}
