package cn.milai.ib.client.game.form;

import cn.milai.ib.client.game.obj.GameEntity;

public interface GameEventListener {

	void onGameObjectDead(GameEntity obj);
	
	void onFormClosed();
	
}
