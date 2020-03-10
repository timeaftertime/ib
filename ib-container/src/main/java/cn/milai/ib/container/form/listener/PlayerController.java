package cn.milai.ib.container.form.listener;

import org.springframework.core.annotation.Order;

import cn.milai.ib.obj.Player;

/**
 * 玩家控制器
 * @author milai
 */
@Order
public class PlayerController implements KeyboardListener {

	private Player player;

	public PlayerController(Player plane) {
		this.player = plane;
	}

	@Override
	public boolean keyDown(Command e) {
		switch (e) {
			case UP :
				player.setUp();
				break;
			case DOWN :
				player.setDown();
				break;
			case LEFT :
				player.setLeft();
				break;
			case RIGHT :
				player.setRight();
				break;
			case SHOOT :
				player.setShooting();
				break;
			default:
				return false;
		}
		return true;
	}

	@Override
	public boolean keyUp(Command e) {
		switch (e) {
			case UP :
				player.clearUp();
				break;
			case DOWN :
				player.clearDown();
				break;
			case LEFT :
				player.clearLeft();
				break;
			case RIGHT :
				player.clearRight();
				break;
			case SHOOT :
				player.clearShooting();
			default:
				return false;
		}
		return true;
	}

}
