package cn.milai.ib.role;

import cn.milai.ib.actor.Controllable;

/**
 * 玩家角色
 * 2020.01.15
 * @author milai
 */
public interface PlayerRole extends Role, Controllable {

	/**
	 * 获取 {@link Player} 实现
	 * @return
	 */
	Player player();

	@Override
	default int getCamp() { return Camp.PLAYER; }

	/**
	 * 设置 Up 状态
	 */
	default void setUp() {
		if (player().setUp()) {
			onSetUp();
		}
	}

	/**
	 * Up 状态被设置时调用
	 */
	default void onSetUp() {}

	/**
	 * 清除 Up 状态
	 */
	default void clearUp() {
		if (player().clearUp()) {
			onClearUp();
		}
	}

	/**
	 * Up 状态被清除时调用
	 */
	default void onClearUp() {}

	/**
	 * 是否设置 Up 状态
	 * @return
	 */
	default boolean isUp() { return player().isUp(); }

	/**
	 * 设置 Down 的状态
	 */
	default void setDown() {
		if (player().setDown()) {
			onSetDown();
		}
	}

	/**
	 * Down 状态被设置时调用
	 */
	default void onSetDown() {}

	/**
	 * 清除 Down 状态
	 */
	default void clearDown() {
		if (player().clearDown()) {
			onClearDown();
		}
	}

	/**
	 * Down 状态被清除时调用
	 */
	default void onClearDown() {}

	/**
	 * 是否设置 Down 状态
	 * @return
	 */
	default boolean isDown() { return player().isDown(); }

	/**
	 * 设置 Left 状态
	 */
	default void setLeft() {
		if (player().setLeft()) {
			onSetLeft();
		}
	}

	/**
	 * Left 状态被设置时调用
	 */
	default void onSetLeft() {}

	/**
	 * 清除 Left 状态
	 */
	default void clearLeft() {
		if (player().clearLeft()) {
			onClearLeft();
		}
	}

	/**
	 * Left 状态被清除时调用
	 */
	default void onClearLeft() {}

	/**
	 * 是否设置 Left 状态
	 * @return
	 */
	default boolean isLeft() { return player().isLeft(); }

	/**
	 * 设置 Right状态
	 */
	default void setRight() {
		if (player().setRight()) {
			onSetRight();
		}
	}

	/**
	 * 设置 Right 状态时调用
	 */
	default void onSetRight() {}

	/**
	 * 清除 Right 状态
	 */
	default void clearRight() {
		if (player().clearRight()) {
			onClearRight();
		}
	}

	/**
	 * 清除 Right 状态时调用
	 */
	default void onClearRight() {}

	/**
	 * 是否设置 Right 状态
	 * @return
	 */
	default boolean isRight() { return player().isRight(); }

	/**
	 * 设置 A 命令状态
	 */
	default void setA() {
		if (player().setA()) {
			onSetA();
		}
	}

	/**
	 * 设置 A 命令时调用
	 */
	default void onSetA() {}

	/**
	 * 清除 A 命令状态
	 */
	default void clearA() {
		if (player().clearA()) {
			onClearA();
		}
	}

	/**
	 * 清除 A 命令时调用
	 */
	default void onClearA() {}

	/**
	 * 是否设置 A 状态
	 * @return
	 */
	default boolean isA() { return player().isA(); }

	/**
	 * 保存当前状态
	 * @param createNew 是否一定创建新状态
	 */
	default void pushStatus(boolean createNew) {
		player().pushStatus(createNew);
	}

}
