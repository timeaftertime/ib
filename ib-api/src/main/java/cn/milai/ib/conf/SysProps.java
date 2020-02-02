package cn.milai.ib.conf;

/**
 * 全局属性 key 常量
 * @author milai
 * @date 2020.02.01
 */
public class SysProps {

	/** 
	 * 整个游戏的尺寸比例，所有对象的大小、速度应乘以该常量
	 */
	static final String KEY_SIZE_RATIO = "ib.size";

	/** 
	 * 整个游戏的速度比例，帧间隔时间应乘以该常量
	 */
	static final String KET_SPEED_RATIO = "ib.speed";

	/**
	 * 中央仓库的地址
	 */
	static final String KEY_REPO_ADDRESS = "ib.repo.address";

	/**
	 * 图片文件根目录
	 */
	static final String KEY_RESOURCE_DIR = "ib.resource.dir";

	/**
	 * 系统配置文件在 classpath 下的路径
	 */
	static final String CONF_FILE = "/ib.conf";

}
