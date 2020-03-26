package cn.milai.ib.container.listener;

/**
 * Container 可以接受的指令
 * @author milai
 * @date 2020.03.23
 */
public enum Command {

	// 暂停 指令
	PAUSE,
	// 上 指令
	UP,
	DOWN,
	// 下 指令
	LEFT,
	// 右 指令
	RIGHT,
	// ABCD 四个动作指令
	A, B, C, D,
	// 按下 指令
	PRESSED
}
