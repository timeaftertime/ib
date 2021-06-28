package cn.milai.ib.conf;

import cn.milai.ib.IBCore;
import cn.milai.ib.config.Configurable;

/**
 * Infinity Battle 全局配置
 * @author milai
 * @date 2021.06.11
 */
public class IBConf {

	private IBConf() {}

	private static CoreConf conf = IBCore.getBean(CoreConf.class);

	private static RepoConf repoConf = IBCore.getBean(RepoConf.class);

	/**
	 * 按照 {@link CoreConf#getSpeed()} 设置的比例缩放指定时间
	 * @param time
	 * @return
	 */
	public static int frameProrate(long time) {
		return (int) (time / conf.getSpeed());
	}

	/**
	 * 获取当前游戏刷新时间间隔（微秒）
	 * @return
	 */
	public static long refreshMillisec() {
		return frameProrate(50L);
	}

	/**
	 * 获取 gif 图片刷新帧数
	 * @return
	 */
	public static int imageUpdateFrame() {
		return conf.getImageUpdateFrame();
	}

	/**
	 * 获取配置文件不存在时是否需要初始化配置到文件
	 * @return
	 */
	public static boolean saveConfigFile() {
		return conf.isSaveConfigFile();
	}

	/**
	 * 获取 {@link Configurable} 配置文件根目录
	 * @return
	 */
	public static String configFilePath() {
		return repoConf.getLocalResourcePath() + "config/";
	}

}
