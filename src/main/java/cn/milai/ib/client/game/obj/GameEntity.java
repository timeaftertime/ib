package cn.milai.ib.client.game.obj;

import cn.milai.ib.client.game.form.GameForm;
import cn.milai.ib.client.game.obj.property.Alive;
import cn.milai.ib.client.game.obj.property.Explosible;

public abstract class GameEntity extends GameObject implements Alive {

	public GameEntity(int x, int y, int width, int height, GameForm container) {
		super(x, y, width, height, container);
	}
	
	@Override
	public void onDead() {
		if(this instanceof Explosible)
			getContainer().addGameObject(new Bomb(getX(), getY(), getContainer()));
	}
	
}
