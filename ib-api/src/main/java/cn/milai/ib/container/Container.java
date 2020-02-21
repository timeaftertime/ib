package cn.milai.ib.container;

import java.awt.Image;
import java.util.List;

import cn.milai.ib.container.listener.GameEventListener;
import cn.milai.ib.container.listener.RefreshListener;
import cn.milai.ib.obj.IBObject;

/**
 * 游戏对象的容器
 * 2019.11.16
 * @author milai
 */
public interface Container {

	/**
	 * 获取 X 坐标
	 * @return
	 */
	int getX();

	/**
	 * 获取 Y 坐标
	 * @return
	 */
	int getY();

	/**
	 * 获取宽度
	 * @return
	 */
	int getWidth();

	/**
	 * 获取长度
	 * @return
	 */
	int getHeight();

	/**
	 * 获取（去掉上边框后）实际游戏界面高度
	 * @return
	 */
	int getContentHeight();

	/**
	 * 向容器中添加游戏对象
	 * 
	 * @param obj
	 */
	public void addObject(IBObject obj);

	/**
	 * 从容器中移除游戏对象
	 * 
	 * @param obj
	 */
	public void removeObject(IBObject obj);

	/**
	 * 获得容器中属于指定类型及其子类的游戏对象列表
	 * 
	 * @param type
	 * @return
	 */
	<T> List<T> getAll(Class<T> type);

	/**
	 * 获取当前累计的帧数，获取失败则返回 -1
	 * 
	 * @return
	 */
	long currentFrame();

	/**
	 * 添加帧刷新事件的 listener
	 * 
	 * @param listener
	 */
	void addRefreshListener(RefreshListener listener);

	/**
	 * 移除帧刷新事件的 listener
	 * 
	 * @param listener
	 */
	void removeRefreshListener(RefreshListener listener);

	/**
	 * 添加游戏事件监听器
	 * 
	 * @param listener
	 */
	void addGameEventListener(GameEventListener listener);

	/**
	 * 播放音频
	 */
	void playAudio(Audio audio);

	/**
	 * 停止一个音频的播放
	 * @param code
	 */
	void stopAudio(String code);

	/**
	 * 设置背景图片
	 * @param img
	 */
	void setBackgroud(Image img);

}
