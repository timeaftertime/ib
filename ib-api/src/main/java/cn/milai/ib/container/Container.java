package cn.milai.ib.container;

import java.util.List;

import cn.milai.ib.IBObject;
import cn.milai.ib.container.listener.ContainerEventListener;

/**
 * 游戏对象的容器
 * 2019.11.16
 * @author milai
 */
public interface Container {

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
	 * 重新设置容器的宽度和高度并保持中心位置不变
	 * @param width
	 * @param height
	 */
	void resize(int width, int height);

	/**
	 * 向容器中添加游戏对象
	 * 若操作失败将抛出 IBContainerException
	 * @param obj
	 */
	public void addObject(IBObject obj) throws IBContainerException;

	/**
	 * 从容器中移除游戏对象
	 * 若操作失败将抛出 IBContainerException
	 * @param obj
	 * @throws IBContainerException
	 */
	public void removeObject(IBObject obj) throws IBContainerException;

	/**
	 * 获得容器中属于指定类型及其子类的游戏对象列表
	 * 若获取失败将抛出 IBContainerException
	 * @param type
	 * @return
	 * @throws IBContainerException
	 */
	<T> List<T> getAll(Class<T> type) throws IBContainerException;

	/**
	 * 获取当前累计的帧数，获取失败则返回 -1
	 * 
	 * @return
	 */
	long getFrame();

	/**
	 * 添加游戏事件监听器
	 * @param listener
	 */
	void addEventListener(ContainerEventListener listener);

	/**
	 * 移除游戏事件监听器
	 * @param listener
	 */
	void removeEventListener(ContainerEventListener listener);

	/**
	 * 播放音频
	 * 若操作失败将抛出 IBContainerException
	 * @param audio
	 * @throws IBContainerException
	 */
	void playAudio(Audio audio) throws IBContainerException;

	/**
	 * 停止一个音频的播放
	 * @param code
	 * @throws IBContainerException
	 */
	void stopAudio(String code) throws IBContainerException;

	/**
	 * 设置背景图片
	 * 若操作失败将抛出 IBContainerException
	 * @param img
	 * @throws IBContainerException
	 */
	void setBackgroud(Image img) throws IBContainerException;

	/**
	 * 获取纪元
	 * @return
	 */
	int getEpoch();

	/**
	 * 重置容器
	 * 移除所有游戏对象和监听器，帧数不会清零
	 * {@link ContainerEventListener#afterEpochChanged(Container)} 将被调用
	 * {@link ContainerEventListener#onObjectRemoved(IBObject)} 不会被调用
	 */
	void reset();
}
