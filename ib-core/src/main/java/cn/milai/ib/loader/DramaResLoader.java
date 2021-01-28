package cn.milai.ib.loader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import cn.milai.ib.conf.PathConf;
import cn.milai.ib.ex.IBIOException;
import cn.milai.ib.loader.ex.DramaResourceNotFoundException;
import cn.milai.ib.util.FileUtil;
import cn.milai.ib.util.HttpUtil;
import cn.milai.ib.util.IOUtil;

/**
 * 剧本相关资源加载器
 * @author milai
 * @date 2020.02.19
 */
public class DramaResLoader {

	private static final Logger LOG = LoggerFactory.getLogger(DramaResLoader.class);

	/**
	 * 用于表示剧本对应资源是否已经存在于本地的文件名
	 */
	private static final String CHECK_FILE = ".ib";

	/**
	 * 已经加载到内存的资源
	 * drama -> { resource -> 资源字节数组 } 
	 */
	private static final Map<String, Map<String, byte[]>> RESOURCES = Maps.newConcurrentMap();

	/**
	 * 获取 drama 下的资源 resource 的输入流
	 * 已经被加载的资源会一直存在内存中，下次获取时直接返回，直到被回收
	 * @param dramaCode
	 * @param resource 不以 / 开始的资源文件相对路径
	 * @return
	 */
	public static InputStream load(String dramaCode, String resource) {
		byte[] data = RESOURCES.computeIfAbsent(dramaCode, c -> loadResource(c)).get(resource);
		if (data == null) {
			throw new DramaResourceNotFoundException(dramaCode, resource);
		}
		return IOUtil.toInputStream(data);
	}

	/**
	 * 加载 drama 对应的资源文件到内存
	 * @param dramaCode
	 */
	public static Map<String, byte[]> load(String dramaCode) {
		return RESOURCES.computeIfAbsent(dramaCode, c -> loadResource(dramaCode));
	}

	/**
	 * 将指定 drama 下所有资源回收，从内存中去除
	 * @param dramaCode
	 */
	public static void expire(String dramaCode) {
		RESOURCES.remove(dramaCode);
	}

	private static Map<String, byte[]> loadResource(String dramaCode) {
		String basePath = PathConf.dramaResPath(dramaCode);
		ensureResourceDirectory(basePath);
		ensureResourceFiles(basePath, dramaCode);
		return doLoadResources(dramaCode, basePath);
	}

	private static Map<String, byte[]> doLoadResources(String dramaCode, String basePath) {
		Map<String, byte[]> resources = Maps.newConcurrentMap();
		for (File file : new File(basePath).listFiles()) {
			if (file.getName().equals(CHECK_FILE)
				|| file.getName().equals(tarGzFileName(dramaCode))) {
				continue;
			}
			resources.putAll(loadFilesFrom("", file));
		}
		return resources;
	}

	/**
	 * 读取文件或读取文件夹中所有文件
	 * @param prefix
	 * @param now
	 * @return
	 */
	private static Map<String, byte[]> loadFilesFrom(String prefix, File now) {
		Map<String, byte[]> resources = Maps.newConcurrentMap();
		if (!now.isDirectory()) {
			resources.put(prefix + "/" + now.getName(), FileUtil.read(now));
			return resources;
		}
		for (File child : now.listFiles()) {
			resources.putAll(loadFilesFrom(prefix + "/" + now.getName(), child));
		}
		return resources;
	}

	/**
	 * 确保资源文件在本地存在，若不存在，尝试从远程获取
	 * @param basePath
	 * @param dramaCode
	 */
	private static void ensureResourceFiles(String basePath, String dramaCode) {
		File checkFile = new File(basePath + "/" + CHECK_FILE);
		if (checkFile.exists()) {
			return;
		}
		LOG.info("剧本 {} 的资源文件不存在，尝试从本地压缩文件获取……", dramaCode);
		String zipFile = basePath + "/" + tarGzFileName(dramaCode);
		if (!new File(zipFile).exists()) {
			LOG.info("剧本 {} 的本地压缩文件不存在，尝试从远程服务器获取……", dramaCode);
			FileUtil.save(
				zipFile,
				HttpUtil.getFile(PathConf.dramaResRepo(dramaCode))
			);
		}
		extract(basePath, tarGzFileName(dramaCode));
	}

	/**
	 * 确保 basePath 存在，若不存在，尝试创建
	 * @param basePath
	 */
	private static void ensureResourceDirectory(String basePath) {
		File directory = new File(basePath);
		if (!directory.isDirectory()) {
			if (!directory.mkdirs()) {
				LOG.error("创建剧本文件夹失败，path = {}", basePath);
				throw new IBIOException(String.format("创建剧本资源文件夹失败，path = {}", basePath));
			}
		}
	}

	/**
	 * 资源包压缩文件名
	 * @param basePath 
	 * @param dramaCode
	 * @return
	 */
	private static String tarGzFileName(String dramaCode) {
		return dramaCode + ".zip";
	}

	/**
	 * 解压 basePath 下指定 .zip 文件到 basePath
	 * @param basePath
	 * @param fileName
	 */
	private static final void extract(String basePath, String fileName) {
		File file = new File(basePath + "/" + fileName);
		LOG.info("开始解压剧本资源文件, file = {}", file);
		try (ZipFile zip = new ZipFile(file)) {
			Enumeration<? extends ZipEntry> entries = zip.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
				LOG.debug("正在解压剧本资源文件, file = {}, entry = {}", file, entry.getName());
				String pathname = basePath + "/" + entry.getName();
				if (entry.isDirectory()) {
					new File(pathname).mkdir();
				} else {
					FileUtil.save(pathname, IOUtil.toBytes(zip.getInputStream(entry)));
				}
			}
			if (!new File(basePath + "/" + CHECK_FILE).createNewFile()) {
				LOG.warn("创建" + CHECK_FILE + "失败，文件可能已经存在");
			}
		} catch (IOException e) {
			LOG.error("解压资源文件未知错误, file = {}, error = {}", fileName, ExceptionUtils.getStackTrace(e));
			throw new IBIOException(String.format("解压资源文件未知错误：file = {}", fileName));
		}

		LOG.info("解压剧本资源文件完成, file = {}", file);
	}
}
