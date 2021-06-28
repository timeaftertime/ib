package cn.milai.ib.loader;

import java.io.File;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.milai.common.ex.unchecked.Uncheckeds;
import cn.milai.common.http.Https;
import cn.milai.common.io.Files;
import cn.milai.common.io.InputStreams;
import cn.milai.ib.conf.PathConf;
import cn.milai.ib.ex.IBIOException;

/**
 * 剧本相关资源加载器
 * @author milai
 * @date 2020.02.19
 */
public class DramaResLoader {

	private DramaResLoader() {}

	private static final Logger LOG = LoggerFactory.getLogger(DramaResLoader.class);

	private static final ResourceLoader LOADER = new ResourceLoader();

	/**
	 * 用于表示剧本对应资源是否已经存在于本地的文件名
	 */
	private static final String CHECK_FILE = ".ib";

	/**
	 * 获取 drama 下的资源 resource 的输入流
	 * 已经被加载的资源会一直存在内存中，下次获取时直接返回，直到被回收
	 * @param dramaCode
	 * @param resource 不以 / 开始的资源文件相对路径
	 * @return
	 */
	public static InputStream load(String dramaCode, String resource) {
		Map<String, byte[]> resources = load(dramaCode);
		byte[] data = resources.get(resource);
		if (data == null) {
			String msg = String.format("资源未找到: dramaCode = %s, resource = %s", dramaCode, resource);
			LOG.error(msg);
			throw new IBIOException(msg);
		}
		return InputStreams.fromBytes(data);
	}

	/**
	 * 加载 drama 对应的资源文件到内存
	 * @param dramaCode
	 */
	public static Map<String, byte[]> load(String dramaCode) {
		String basePath = PathConf.codeToPath(dramaCode);
		ensureResourceDirectory(basePath);
		ensureResources(basePath, dramaCode);
		return LOADER.load(dramaCode, true);
	}

	/**
	 * 将指定 drama 下所有资源回收，从内存中去除
	 * @param dramaCode
	 * @return 之前缓存的资源
	 */
	public static Map<String, byte[]> unload(String dramaCode) {
		return LOADER.unload(dramaCode);
	}

	/**
	 * 确保资源文件在本地存在，若不存在，尝试从远程获取
	 * @param basePath
	 * @param dramaCode
	 */
	private static void ensureResources(String basePath, String dramaCode) {
		File checkFile = new File(basePath + "/" + CHECK_FILE);
		if (checkFile.exists()) {
			return;
		}
		LOG.info("剧本 {} 的资源文件不存在，尝试从本地压缩文件获取……", dramaCode);
		String zipFileName = zipFileName(dramaCode);
		String zipFile = PathConf.dramaResZipPath() + zipFileName;
		if (!Files.exists(zipFile)) {
			LOG.info("剧本 {} 的本地压缩文件不存在，尝试从远程服务器获取……", dramaCode);
			Files.saveRethrow(zipFile, Https.getFile(PathConf.dramaResRepo(dramaCode)));
		}
		extract(basePath, new File(zipFile));
	}

	/**
	 * 确保 basePath 存在，若不存在，尝试创建
	 * @param basePath
	 */
	private static void ensureResourceDirectory(String basePath) {
		File directory = new File(basePath);
		if (!directory.isDirectory()) {
			if (!directory.mkdirs()) {
				String msg = String.format("创建剧本资源文件夹失败: path = %s", basePath);
				LOG.error(msg);
				throw new IBIOException(msg);
			}
		}
	}

	/**
	 * 资源包压缩文件名
	 * @param basePath 
	 * @param dramaCode
	 * @return
	 */
	private static String zipFileName(String dramaCode) {
		return dramaCode + ".zip";
	}

	/**
	 * 解压指定 .zip 文件到 basePath 下
	 * @param basePath
	 * @param zipFile
	 */
	private static void extract(String basePath, File zipFile) {
		LOG.info("开始解压剧本资源文件: file = {}", zipFile);
		Uncheckeds.rethrow(() -> {
			try (ZipFile zip = new ZipFile(zipFile)) {
				Enumeration<? extends ZipEntry> entries = zip.entries();
				while (entries.hasMoreElements()) {
					ZipEntry entry = entries.nextElement();
					LOG.debug("解压剧本资源文件: file = {}, entry = {}", zipFile, entry.getName());
					String pathname = basePath + "/" + entry.getName();
					if (entry.isDirectory()) {
						Files.mkdir(pathname);
					} else {
						Files.saveRethrow(pathname, InputStreams.toBytes(zip.getInputStream(entry)));
					}
				}
				if (!new File(basePath + "/" + CHECK_FILE).createNewFile()) {
					LOG.warn("创建 {} 失败，文件可能已经存在", CHECK_FILE);
				}
			}
		}, "解压资源文件未知错误: file = %s", zipFile);
		LOG.info("解压剧本资源文件完成: file = {}", zipFile);
	}
}
