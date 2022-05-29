package cn.milai.ib.plugin;

import java.util.Set;
import java.util.function.Consumer;

import cn.milai.ib.stage.Stage;
import cn.milai.ib.stage.event.AddActorEvent;
import cn.milai.ib.stage.event.RemoveActorEvent;
import cn.milai.ib.stage.event.ResizedEvent;
import cn.milai.ib.stage.event.StageClosedEvent;
import cn.milai.ib.stage.event.StageRefreshedEvent;
import cn.milai.ib.stage.event.StageResetedEvent;
import cn.milai.ib.stage.event.StageStartedEvent;

/**
 * {@link Stage} 的 {@link Plugin}
 * @author milai
 * @date 2022.05.15
 */
public interface Crew extends Plugin {

	/**
	 * 获取关联 {@link Stage} 集合
	 * @return
	 */
	@SuppressWarnings("unchecked")
	default Set<Stage> stages() {
		Set<?> stages = pluginables();
		return (Set<Stage>) stages;
	}

	/**
	 * 获取 {@link StageStartedEvent} 监听器，若没有，返回 <code>null</code>。
	 * 多次调用应返回同一个对象
	 * @return
	 */
	default Consumer<StageStartedEvent> onStarted() {
		return null;
	}

	/**
	 * 获取 {@link StageRefreshedEvent} 监听器，若没有，返回 <code>null</code>。
	 * 多次调用应返回同一个对象
	 * @return
	 */
	default Consumer<StageRefreshedEvent> onRefreshed() {
		return null;
	}

	/**
	 * 获取 {@link StageResetedEvent} 监听器，若没有，返回 <code>null</code>。
	 * 多次调用应返回同一个对象
	 * @return
	 */
	default Consumer<StageResetedEvent> onReseted() {
		return null;
	}

	/**
	 * 获取 {@link StageClosedEvent} 监听器，若没有，返回 <code>null</code>。
	 * 多次调用应返回同一个对象
	 * @return
	 */
	default Consumer<StageClosedEvent> onClosed() {
		return null;
	}

	/**
	 * 获取 {@link AddActorEvent} 监听器，若没有，返回 <code>null</code>。
	 * 多次调用应返回同一个对象
	 * @return
	 */
	default Consumer<AddActorEvent> onAddActor() {
		return null;
	}

	/**
	 * 获取 {@link RemoveActorEvent} 监听器，若没有，返回 <code>null</code>。
	 * 多次调用将返回同一个对象
	 * @return
	 */
	default Consumer<RemoveActorEvent> onRemoveActor() {
		return null;
	}

	/**
	 * 获取 {@link ResizedEvent} 监听器，若没有，返回 <code>null</code>。
	 * 多次调用将返回同一个对象
	 * @return
	 */
	default Consumer<ResizedEvent> onResized() {
		return null;
	}
}
