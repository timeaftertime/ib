package cn.milai.ib.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.milai.ib.ex.IBIOException;

/**
 * 文件相关工具类
 * @author milai
 * @date 2020.02.01
 */
public class FileUtil {

	/**
	 * 保存文件
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
			e.printStackTrace();
		}
	}

	/**
	 * 读取文件
	 * @param file
	 * @return
	 */
	public static byte[] read(File file) {
		try {
			return ByteUtil.toBytes(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			throw new IBIOException(e);
		}
	}

}
