package cn.milai.ib.form;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JFrame;

import cn.milai.ib.EventNotifier;
import cn.milai.ib.GameObject;

public abstract class GameForm extends JFrame implements EventNotifier {

	private static final long serialVersionUID = 1L;

	private static final Map<GameObject, MouseListener> listeners = new ConcurrentHashMap<>();

	public GameForm() {
		addMouseListener(new MouseAdapter() {

			private MouseListener getTarget(MouseEvent e) {
				for (GameObject obj : listeners.keySet()) {
					if (obj.containPoint(e.getX(), e.getY())) {
						return listeners.remove(obj);
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

	@Override
	public void notifyOnce(GameObject gameObj, MouseListener listener) {
		listeners.put(gameObj, listener);
	}

}