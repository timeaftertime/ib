package cn.milai.ib.conf;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.milai.ib.IBCore;
import cn.milai.ib.loader.ex.ConfigNotFoundException;
import cn.milai.ib.loader.ex.ImageNotFoundException;

/**
 * 路径相关配置
 * @author milai
 * @date 2020.02.02
 */
public class PathConf {

	private static final Logger log = LoggerFactory.getLogger(PathConf.class);

	private static RepoConf repoConf = IBCore.getBean(RepoConf.class);

	private static final String CONF_PREFIX = "conf/";
	private static final String CONF_SUFFIX = ".conf";

	private static final String IMG_PREFIX = "img/";
	private static final String IMG_SUFFIX = ".gif";
	private static final String IMG_STATUS_SPLIT = "$";

	private static final String DRAMA_PREFIX = "drama/";
	private static final String DRAMA_SUFFIX = ".drama";

	private static final String DRAMA_RES_PREFFIX = "dramaRes/";

	private PathConf() {}

	/**
	 * 获取 clazz 图片的本地绝对路径
	 * @param clazz
	 * @return
	 */
	public static String imgPath(Class<?> clazz) {
		return imgPath(clazz, null);
	}

	/**
	 * 获取 clazz 在 status 状态图片的本地绝对路径
	 * @param clazz
	 * @param status
	 * @return
	 */
	public static String imgPath(Class<?> clazz, String status) {
		String statusAppend = (status == null) ? "" : (IMG_STATUS_SPLIT + status);
		return repoConf.getLocalResourcePath() + IMG_PREFIX + toPath(clazz) + statusAppend + IMG_SUFFIX;
	}

	/**
	 * 获取 clazz 配置文件的本地绝对路径
	 * @param clazz
	 * @return
	 */
	public static String confPath(Class<?> clazz) {
		return repoConf.getLocalResourcePath() + CONF_PREFIX + toPath(clazz) + CONF_SUFFIX;
	}

	/**
	 * 获取剧本文件的本地绝对路径
	 * @param dramaCode
	 * @return
	 */
	public static String dramaPath(String dramaCode) {
		return repoConf.getLocalResourcePath() + DRAMA_PREFIX + toPath(dramaCode) + DRAMA_SUFFIX;
	}

	/**
	 * 获取剧本资源文件夹的本地绝对路径
	 * @param dramaCode
	 * @return
	 */
	public static String dramaResPath(String dramaCode) {
		return repoConf.getLocalResourcePath() + DRAMA_RES_PREFFIX + toPath(dramaCode);
	}

	/**
	 * 获取 class path 下图片输入流
	 * @param clazz
	 * @param status
	 * @return
	 * @throws ImageNotFoundException 若图片文件不存在
	 */
	public static InputStream imgStream(Class<?> clazz, String status) throws ImageNotFoundException {
		String statusAppend = (status == null) ? "" : (IMG_STATUS_SPLIT + status);
		String path = "/" + IMG_PREFIX + toPath(clazz) + statusAppend + IMG_SUFFIX;
		InputStream in = clazz.getResourceAsStream(path);
		if (in == null) {
			log.error(
				String.format(
					"获取图片输入流失败：class = %s, status = %s, file = classpath:%s",
					clazz.getName(), status, path
				)
			);
			throw new ImageNotFoundException(clazz, status);
		}
		return in;
	}

	/**
	 * 获取 class path 下配置文件输入流
	 * @param clazz
	 * @return
	 * @throws ConfigNotFoundException 若配置文件不存在
	 */
	public static InputStream confStream(Class<?> clazz) throws ConfigNotFoundException {
		String path = "/" + CONF_PREFIX + toPath(clazz) + CONF_SUFFIX;
		InputStream in = clazz.getResourceAsStream(path);
		if (in == null) {
			log.error(
				String.format(
					"获取配置文件输入流失败：class = %s, file = classpath:%s",
					clazz.getName(), path
				)
			);
			throw new ConfigNotFoundException(clazz);
		}
		return in;
	}

	/**
	 * 获取远程 drama 文件 url
	 * @param clipCode
	 * @return
	 */
	public static String dramaRepo(String clipCode) {
		return repoConf.getRemoteUrl() + "/file/drama?dramaCode=" + clipCode;
	}

	/**
	 * 获取远程 drama 资源文件 url
	 * @param clipCode
	 * @return
	 */
	public static String dramaResRepo(String clipCode) {
		return repoConf.getRemoteUrl() + "/file/dramaRes?dramaCode=" + clipCode;
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
