package cn.milai.ib.character;

import cn.milai.ib.Player;
import cn.milai.ib.container.listener.Command;

/**
 * 玩家角色
 * 2020.01.15
 * @author milai
 */
public interface PlayerCharacter extends Player, IBCharacter, Controllable {

	@Override
	default boolean onReceive(Command command) {
		switch (command) {
			case UP : {
				setUp();
				return false;
			}
			case DOWN : {
				setDown();
				return false;
			}
			case LEFT : {
				setLeft();
				return false;
			}
			case RIGHT : {
				setRight();
				return false;
			}
			case A : {
				setA();
				return false;
			}
			default: {
				return true;
			}
		}
	}

	@Override
	default boolean onCancel(Command command) {
		switch (command) {
			case UP : {
				clearUp();
				return false;
			}
			case DOWN : {
				clearDown();
				return false;
			}
			case LEFT : {
				clearLeft();
				return false;
			}
			case RIGHT : {
				clearRight();
				return false;
			}
			case A : {
				clearA();
				return false;
			}
			default: {
				return true;
			}
		}
	}

	@Override
	default int getCamp() {
		return Camp.PLAYER;
	}

}
