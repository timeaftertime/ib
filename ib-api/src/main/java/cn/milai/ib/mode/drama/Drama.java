package cn.milai.ib.mode.drama;

import cn.milai.ib.container.Stage;
import cn.milai.ib.container.plugin.media.Audio;
import cn.milai.ib.container.plugin.ui.Image;

/**
 * 剧本
 * @author milai
 * @date 2020.03.05
 */
public interface Drama {

	/**
	 * 获取剧本唯一标识
	 * @return
	 */
	default String getCode() { return getClass().getName(); }

	/**
	 * 获取剧本名字
	 * @return
	 */
	default String getName() { return getCode(); }

	/**
	 * 在指定容器中运行剧本
	 * 该方法应该尽快响应 Thread.interrup()
	 * 返回时若保留线程中断状态，将退出当前线程
	 * @param container
	 */
	void run(Stage container);

	/**
	 * 读取当前剧本的指定资源并转换为图片
	 * @param resource
	 * @return
	 */
	Image image(String resource);

	/**
	 * 读取当前剧本的指定资源并转换为指定 code 的音频
	 * @param audioCode
	 * @param resource
	 * @return
	 */
	Audio audio(String audioCode, String resource);

	/**
	 * 读取当前剧本的指定字符串
	 * @param strCode
	 * @return
	 */
	String str(String strCode);

}
