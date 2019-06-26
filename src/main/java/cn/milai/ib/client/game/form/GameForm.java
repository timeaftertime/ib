package cn.milai.ib.client.game.form;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JFrame;

import cn.milai.ib.client.game.obj.GameObject;
import cn.milai.ib.client.game.obj.GameTip;

public abstract class GameForm extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final Map<GameTip, MouseListener> tipListeners = new ConcurrentHashMap<>();

	public GameForm() {
		addMouseListener(new MouseAdapter() {

			private MouseListener getTarget(MouseEvent e) {
				for (GameTip tip : tipListeners.keySet()) {
					if (tip.containPoint(e.getX(), e.getY())) {
						return tipListeners.remove(tip);
					}
				}
				return null;
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				MouseListener target = getTarget(e);
				if (target != null) {
					target.mouseClicked(e);
				}
			}
		});
	}

	public void addGameObject(GameObject obj) {

	}

	public void removeGameObject(GameObject obj) {

	}

	public void addOneTimeMouseListener(GameTip gameTip, MouseListener listener) {
		tipListeners.put(gameTip, listener);
	}

}
