package cn.milai.ib.container;

import cn.milai.ib.EventNotifier;
import cn.milai.ib.GameObject;
import cn.milai.ib.container.listener.RefreshListener;

/**
 * 游戏对象的容器
 *
 * 2019.11.16
 *
 * @author milai
 */
public interface Container extends EventNotifier {

	/**
	 * 
	 * @return
	 */
	int getX();

	/**
	 * 
	 * @return
	 */
	int getY();

	/**
	 * 
	 * @return
	 */
	int getWidth();

	/**
	 * 
	 * @return
	 */
	int getHeight();
	
	/**
	 * 获取（去掉上边框后）实际游戏界面高度
	 * @return
	 */
	int getContentHeight();
	
	/**
	 * 
	 * @return
	 */
	String getTitle();
	
	/**
	 * 
	 */
	void setTitle(String title);

	/**
	 * 向容器中添加游戏对象
	 * @param obj
	 */
	public void addGameObject(GameObject obj);

	/**
	 * 从容器中移除游戏对象
	 * @param obj
	 */
	public void removeGameObject(GameObject obj);

	/**
	 * 获得容器中属于指定类型及其子类的游戏对象个数
	 * @param type
	 * @return
	 */
	int countOf(Class<? extends GameObject> type);
	
	/**
	 * 获取当前累计的帧数
	 * @return
	 */
	 long currentFrame();
	 
	 /**
	  * 添加帧刷新事件的 listener
	  * @param listener
	  */
	 void addRefreshListener(RefreshListener listener);
	 
	 /**
	  * 移除帧刷新事件的 listener
	  * @param listener
	  */
	 void removeRefreshListener(RefreshListener listener);
}
