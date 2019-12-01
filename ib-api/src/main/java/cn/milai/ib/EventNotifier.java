package cn.milai.ib;

import java.awt.event.MouseListener;

import cn.milai.ib.obj.IBObject;

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
	void notifyOnce(IBObject gameObj, MouseListener listener);

}
