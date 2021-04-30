package cn.milai.ib.loader;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.milai.common.io.Files;

/**
 * 目录加载器
 * @author milai
 * @date 2021.04.29
 */
public class DirectoryLoader {

	private DirectoryLoader() {}

	/**
	 * 加载指定目录下所有文件，返回文件相对路径(以 / 开始)到字节数据的映射
	 * @param dir
	 * @return
	 */
	public static Map<String, byte[]> load(String dir) {
		return load(new File(dir));
	}

	/**
	 * 加载指定目录下所有文件，返回文件相对路径(以 / 开始)到字节数据的映射
	 * @param dir
	 * @return
	 */
	public static Map<String, byte[]> load(File dir) {
		return loadFrom("", dir);
	}

	/**
	 * 读取文件或读取文件夹中所有文件
	 * @param prefix
	 * @param now
	 * @return
	 */
	private static Map<String, byte[]> loadFrom(String prefix, File now) {
		Map<String, byte[]> resources = new ConcurrentHashMap<>();
		for (File child : now.listFiles()) {
			if (!child.isDirectory()) {
				resources.put(prefix + "/" + child.getName(), Files.toBytes(child));
			} else {
				resources.putAll(loadFrom(prefix + "/" + child.getName(), child));
			}
		}
		return resources;
	}

}
