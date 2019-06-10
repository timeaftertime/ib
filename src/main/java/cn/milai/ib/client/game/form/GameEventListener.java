package cn.milai.ib.client.game.form;

import cn.milai.ib.client.game.GameObject;

public interface GameEventListener {

	void onGameObjectDead(GameObject obj);
	
	void onFormClosed();
	
}
