package cn.milai.ib.client.game.form;

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
		addMouseListener(new MouseListener() {

			private GameTip getTarget(MouseEvent e) {
				for (GameTip tip : tipListeners.keySet()) {
					if (tip.containPoint(e.getX(), e.getY())) {
						return tip;
					}
				}
				return null;
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				GameTip target = getTarget(e);
				if (target != null) {
					tipListeners.get(target).mouseClicked(e);
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				GameTip target = getTarget(e);
				if (target != null) {
					tipListeners.get(target).mousePressed(e);
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				GameTip target = getTarget(e);
				if (target != null) {
					tipListeners.get(target).mouseReleased(e);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				GameTip target = getTarget(e);
				if (target != null) {
					tipListeners.get(target).mouseEntered(e);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				GameTip target = getTarget(e);
				if (target != null) {
					tipListeners.get(target).mouseExited(e);
				}
			}

		});
	}

	public void addGameObject(GameObject obj) {

	}

	public void removeGameObject(GameObject obj) {

	}

	public void addMouseListener(GameTip gameTip, MouseListener listener) {
		tipListeners.put(gameTip, listener);
	}

}
