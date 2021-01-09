package cn.milai.ib.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.milai.ib.ex.IBIOException;

/**
 * 文件相关工具类
 * @author milai
 * @date 2020.02.01
 */
public class FileUtil {

	private static final Logger LOG = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * 保存数据到 path 指向的文件
	 * 若中间目录不存在，将被创建
	 * 若指定文件已经存在，将被覆盖
	 * @param path
	 * @param data
	 */
	public static void save(String path, byte[] data) {
		try {
			File file = new File(path);
			new File(file.getParent()).mkdirs();
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(data);
			fos.close();
		} catch (IOException e) {
			LOG.error("保存文件失败, file = {}, error = {}", path, ExceptionUtils.getStackFrames(e));
			throw new IBIOException(e);
		}
	}

	/**
	 * 保存数据到 file
	 * 若中间目录不存在，将被创建
	 * 若指定文件已经存在，将被覆盖
	 * @param file
	 * @param data
	 */
	public static void save(File file, byte[] data) {
		save(file.getAbsolutePath(), data);
	}

	/**
	 * 读取文件
	 * @param file
	 * @return
	 */
	public static byte[] read(File file) {
		try {
			return IOUtil.toBytes(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			LOG.error("读取文件失败, file = {}, error = {}", file, ExceptionUtils.getStackFrames(e));
			throw new IBIOException(e);
		}
	}

}
