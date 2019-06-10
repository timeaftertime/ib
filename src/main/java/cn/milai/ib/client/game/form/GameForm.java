package cn.milai.ib.client.game.form;

import javax.swing.JFrame;

import cn.milai.ib.client.game.GameObject;

public abstract class GameForm extends JFrame{

	private static final long serialVersionUID = 1L;
	
	public void addGameObject(GameObject obj) {
	};
	
	public void removeGameObject(GameObject obj) {
	};	
	
}
