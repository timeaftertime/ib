package cn.milai.ib.client.game.form.listener;

import cn.milai.ib.client.game.form.Command;
import cn.milai.ib.client.game.obj.plane.PlayerPlane;

/**
 * 玩家飞机控制器
 * 
 * @author milai
 *
 */
public class PlayerController implements KeyboardListener {

	private PlayerPlane plane;

	public PlayerController(PlayerPlane plane) {
		this.plane = plane;
	}

	@Override
	public void keyDown(Command e) {
		switch (e) {
		case UP:
			plane.setUp();
			break;
		case DOWN:
			plane.setDown();
			break;
		case LEFT:
			plane.setLeft();
			break;
		case RIGHT:
			plane.setRight();
			break;
		case SHOOT:
			plane.setShooting();
			break;
		default:
			break;
		}
	}

	@Override
	public void keyUp(Command e) {
		switch (e) {
			case UP:
				plane.clearUp();
				break;
			case DOWN:
				plane.clearDown();
				break;
			case LEFT:
				plane.clearLeft();
				break;
			case RIGHT:
				plane.clearRight();
				break;
			case SHOOT:
				plane.clearShooting();
			default:
				break;
		}
	}

}
