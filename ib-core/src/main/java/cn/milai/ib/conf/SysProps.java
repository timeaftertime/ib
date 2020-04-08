package cn.milai.ib.conf;

/**
 * 全局属性 key 常量
 * @author milai
 * @date 2020.02.01
 */
public interface SysProps {

	/** 
	 * 整个游戏的尺寸比例，所有对象的大小、速度应乘以该常量
	 */
	String SIZE_RATIO = "ib.size";

	/** 
	 * 整个游戏的速度比例，帧间隔时间应乘以该常量
	 */
	String SPEED_RATIO = "ib.speed";

	/**
	 * 中央仓库的地址
	 */
	String REPO_ADDRESS = "ib.repo.address";

	/**
	 * 资源图片文件根目录
	 */
	String RESOURCE_PATH = "ib.resource.path";

	/**
	 * 系统配置文件在 classpath 下的路径
	 */
	String CONF_FILE = "/ib.conf";

	/**
	 * GIF 图片更新到下一张所间隔的帧数
	 */
	String IMAGE_UPDATE_FRAME = "ib.image.update.frame";

}
