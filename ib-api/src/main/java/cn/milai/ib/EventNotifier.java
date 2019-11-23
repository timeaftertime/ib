package cn.milai.ib;

import java.awt.event.MouseListener;

/**
 * 事件传播者
 *
 * 2019.11.16
 *
 * @author milai
 */
public interface EventNotifier {
	
	/**
	 * 将下一个与 gameObj 关联的鼠标事件通知 listener
	 * @param gameObj
	 * @param listener
	 */
	void notifyOnce(GameObject gameObj, MouseListener listener);

}
