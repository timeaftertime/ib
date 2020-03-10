package cn.milai.ib.container.form;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JFrame;

import cn.milai.ib.obj.IBObject;

/**
 * FormContainer 抽象基类
 * @author milai
 */
public abstract class AbstactFormContainer extends JFrame implements FormContainer {

	private static final long serialVersionUID = 1L;

	private static final Map<FormComponent, MouseListener> listeners = new ConcurrentHashMap<>();

	public AbstactFormContainer() {
		addMouseListener(new MouseAdapter() {

			private MouseListener getTarget(MouseEvent e) {
				for (IBObject obj : listeners.keySet()) {
					if (obj.containsPoint(e.getX(), e.getY())) {
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
	public void notifyOnce(FormComponent component, MouseListener listener) {
		listeners.put(component, listener);
	}

}
