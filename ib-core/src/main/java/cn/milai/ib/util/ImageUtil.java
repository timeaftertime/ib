package cn.milai.ib.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.ex.IBIOException;

/**
 * 图片相关工具类
 * 2019.11.21
 * @author milai
 */
public abstract class ImageUtil {

	private ImageUtil() {
	}

	private static final Toolkit TK = Toolkit.getDefaultToolkit();
	private static final int DEFAULT_WIDTH = SystemConf.prorate(50);
	private static final int DEFAULT_HEIGHT = SystemConf.prorate(50);

	/**
	 * 加载 url 指定的图片
	 * @param url
	 * @return
	 */
	public static final Image loadImage(URL url) {
		return TK.createImage(url);
	}

	/**
	 * 从输入流读取图片
	 * @param in
	 * @return
	 */
	public static final Image loadImage(InputStream in) {
		return TK.createImage(IOUtil.toBytes(in));
	}

	public static final Image loadImage(File file) {
		try {
			return loadImage(file.toURI().toURL());
		} catch (MalformedURLException e) {
			throw new IBIOException(e);
		}
	}

	/**
	 * 返回 image 的复制
	 * @param image
	 * @return
	 */
	public static BufferedImage copy(BufferedImage image) {
		BufferedImage img = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		img.setData(image.getData());
		return img;
	}

	/**
	 * 创建一张纯色图片
	 * @param color
	 * @param width
	 * @param height
	 * @return
	 */
	public static BufferedImage newImage(Color color, int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		g.setColor(color);
		g.fillRect(0, 0, width, height);
		return image;
	}

	/**
	 * 创建一张背景透明的图片
	 * @param width
	 * @param height
	 * @return
	 */
	public static BufferedImage newImage(int width, int height) {
		Graphics2D g2d = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB).createGraphics();
		BufferedImage img = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
		g2d.dispose();
		return img;
	}

	/**
	 * 创建一个 img 上的指定透明度的画板
	 * @param img
	 * @param transaparency
	 * @return
	 */
	public static Graphics createGraphics(BufferedImage img, float transaparency) {
		Graphics2D g2d = img.createGraphics();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transaparency));
		return g2d;
	}

	/**
	 * 获取 img 的 jpg 格式
	 * @param img
	 * @return
	 */
	public static Image getJPGImage(Image img) {
		BufferedImage image = newImage(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		Graphics g = image.getGraphics();
		g.drawImage(img, 0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT, null);
		return img;
	}

}
