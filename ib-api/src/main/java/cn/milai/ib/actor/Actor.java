package cn.milai.ib.actor;

import java.util.Map;
import java.util.function.Consumer;

import cn.milai.ib.actor.event.ActorEvent;
import cn.milai.ib.actor.nature.Nature;
import cn.milai.ib.geometry.Bounds;
import cn.milai.ib.geometry.Layer;
import cn.milai.ib.stage.Stage;

/**
 * 演员
 * @author milai
 * @date 2021.07.09
 */
public interface Actor extends Bounds, Layer {

	/**
	 * 默认 {@link #getStatus()} 值
	 */
	String STATUS_DEFAULT = "default";

	/**
	 * 获取当前状态
	 * @return
	 */
	String getStatus();

	/**
	 * 设置当前状态
	 * @param status
	 */
	void setStatus(String status);

	/**
	 * 获取所属 {@link Stage}
	 * @return
	 */
	Stage stage();

	/**
	 * 进入指定 {@link Stage}
	 * @param stage
	 */
	void enter(Stage stage);

	/**
	 * 从当前 {@link Stage} 离场
	 */
	void exit();

	/**
	 * 完成初始化并返回自身
	 * @return
	 */
	Actor makeup();

	/**
	 * 是否已经初始化
	 * @return
	 */
	boolean isMakeup();

	/**
	 * 添加一个 {@link #makeup()} 回调，若已经 {@link #isMakeup()}，将立刻回调
	 * @param <T>
	 * @param listener
	 * @return
	 */
	<T extends Actor> T onMakeUp(Consumer<ActorEvent> listener);

	/**
	 * 设置指定类型属性为 {@link Nature}
	 * @param p
	 */
	void putNature(Nature p);

	/**
	 * 获取 {@link Nature#name()} 为指定值的 {@link Nature}，若没有该 {@link Nature}，返回 null
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	<T extends Nature> T getNature(String name);

	/**
	 * 获取是否有指定属性
	 * @param name
	 * @return
	 */
	default boolean hasNature(String name) {
		return getNature(name) != null;
	}

	/**
	 * 获取所有 name -> {@link Nature} 的映射
	 * @return
	 */
	Map<String, Nature> natures();

	@Override
	default int getZ() { return Layers.DEFAULT.getZ(); }

}
