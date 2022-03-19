package cn.milai.ib.conf;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.milai.ib.IBBeans;
import cn.milai.ib.loader.ex.ImageNotFoundException;

/**
 * 路径相关配置
 * @author milai
 * @date 2020.02.02
 */
public class PathConf {

	private PathConf() {}

	private static final Logger LOG = LoggerFactory.getLogger(PathConf.class);

	private static RepoConf repoConf = IBBeans.getBean(RepoConf.class);

	private static final String RES_PREFIX = "res/";

	private static final String IMG_SUFFIX = ".gif";

	private static final String DRAMA_ZIP_RES = "dramaRes";

	/**
	 * 获取 clazz 在 status 状态图片的本地绝对路径
	 * @param clazz
	 * @param status
	 * @return
	 */
	public static String imgFile(Class<?> clazz, String status) {
		return repoConf.getLocalResourcePath() + toResPath(clazz) + status + IMG_SUFFIX;
	}

	public static String dramaResZipPath() {
		return repoConf.getLocalResourcePath() + DRAMA_ZIP_RES + "/";
	}

	/**
	 * 获取指定资源 code 对应本地目录的绝对路径
	 * @param code
	 * @return
	 */
	public static String codeToPath(String code) {
		return repoConf.getLocalResourcePath() + toResPath(code);
	}

	/**
	 * 获取 class path 下图片输入流
	 * @param clazz
	 * @param status
	 * @return
	 * @throws ImageNotFoundException 若图片文件不存在
	 */
	public static InputStream imgStream(Class<?> clazz, String status) throws ImageNotFoundException {
		String path = "/" + toResPath(clazz) + status + IMG_SUFFIX;
		InputStream in = clazz.getResourceAsStream(path);
		if (in == null) {
			LOG.error(
				String.format("获取图片输入流失败：class = %s, status = %s, file = classpath:%s", clazz.getName(), status, path)
			);
			throw new ImageNotFoundException(clazz, status);
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
	private static String toResPath(Class<?> clazz) {
		return toResPath(clazz.getName());
	}

	/**
	 * 获取指定资源 code 对应资源文件目录
	 * @param code
	 * @return
	 */
	private static String toResPath(String code) {
		return RES_PREFIX + code.replace('.', '/') + "/";
	}
}
