package cn.milai.ib.conf;

/**
 * 路径相关配置
 * @author milai
 * @date 2020.02.02
 */
public class PathConf {

	private static final String CONF_PREFIX = "conf/";
	private static final String CONF_SUFFIX = ".conf";

	private static final String IMG_PREFIX = "img/";
	private static final String IMG_SUFFIX = ".gif";
	private static final String IMG_STATUS_SPLIT = "$";

	private static final String DRAMA_PREFIX = "drama/";
	private static final String DRAMA_SUFFIX = ".drama";

	/**
	 * 获取图片文件路径
	 * @return
	 */
	public static String img(Class<?> clazz) {
		return img(clazz, null);
	}

	/**
	 * 获取指定状态的图片文件路径
	 * @return
	 */
	public static String img(Class<?> clazz, String status) {
		String statusAppend = (status == null) ? "" : (IMG_STATUS_SPLIT + status);
		return resourceDir() + IMG_PREFIX + toPath(clazz) + statusAppend + IMG_SUFFIX;
	}

	/**
	 * 获取配置文件路径
	 * @return
	 */
	public static String conf(Class<?> clazz) {
		return resourceDir() + CONF_PREFIX + toPath(clazz) + CONF_SUFFIX;
	}

	/**
	 * 获取剧本文件路径
	 * @param dramaCode
	 * @return
	 */
	public static String drama(String dramaCode) {
		return resourceDir() + DRAMA_PREFIX + toPath(dramaCode) + DRAMA_SUFFIX;
	}

	/**
	 * 获取资源文件目录
	 * @return
	 */
	private static final String resourceDir() {
		return SystemConf.getStr(SysProps.KEY_RESOURCE_DIR);
	}

	/**
	 * 远程图片 url
	 * @param clazz
	 * @return
	 */
	public static String imgRepo(Class<?> clazz, String status) {
		String statusAppend = (status == null) ? "" : ("&status=" + status);
		return SystemConf.repoURL() + "/file/img?characterCode=" + clazz.getName() + statusAppend;
	}

	/**
	 * 远程图片 url
	 * @param clazz
	 * @return
	 */
	public static String imgRepo(Class<?> clazz) {
		return imgRepo(clazz, null);
	}

	/**
	 * 远程配置文件 url
	 * @param clazz
	 * @return
	 */
	public static String confRepo(Class<?> clazz) {
		return SystemConf.repoURL() + "/file/conf?characterCode=" + clazz.getName();
	}

	/**
	 * 远程 drama 文件 url
	 * @param clipCode
	 * @return
	 */
	public static String dramaRepo(String clipCode) {
		return SystemConf.repoURL() + "/file/drama?dramaCode=" + clipCode;
	}

	/**
	 * 获取 class 对应资源文件中间目录
	 * @param clazz
	 * @return
	 */
	public static String toPath(Class<?> clazz) {
		return clazz.getName().replace('.', '/');
	}

	/**
	 * 获取 drama 对应资源文件中间目录
	 * @param dramaCode
	 * @return
	 */
	public static String toPath(String dramaCode) {
		return dramaCode.replace('.', '/');
	}
}
