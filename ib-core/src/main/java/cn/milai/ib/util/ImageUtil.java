package cn.milai.ib.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

import cn.milai.common.ex.unchecked.RethrownException;
import cn.milai.common.ex.unchecked.Uncheckeds;
import cn.milai.common.io.InputStreams;

/**
 * 图片相关工具类
 * 2019.11.21
 * @author milai
 */
public abstract class ImageUtil {

	private ImageUtil() {}

	/**
	 * 加载 url 指定的图片
	 * @param url
	 * @return
	 * @throws RethrownException
	 */
	public static BufferedImage[] loadImage(URL url) throws RethrownException {
		return Uncheckeds.rethrow(() -> loadImage(url.openStream()));
	}

	/**
	 * 从输入流读取图片
	 * @param in
	 * @return
	 * @throws RethrownException
	 */
	public static BufferedImage[] loadImage(InputStream in) throws RethrownException {
		return Uncheckeds.rethrow(() -> {
			BufferedImage[] images;
			byte[] bytes = InputStreams.toBytes(in);
			ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
			reader.setInput(ImageIO.createImageInputStream(InputStreams.toInputStream(bytes)));
			if (reader.getNumImages(true) > 0) {
				images = new BufferedImage[reader.getNumImages(true)];
				for (int i = 0; i < reader.getNumImages(false); i++) {
					images[i] = reader.read(i);
				}
			} else {
				images = new BufferedImage[] { ImageIO.read(InputStreams.toInputStream(bytes)) };
			}
			return images;
		});
	}

	public static final BufferedImage[] loadImage(File file) throws RethrownException {
		return Uncheckeds.rethrow(() -> loadImage(new FileInputStream(file)));
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
	 * 创建一张大小与指定文字相适应的图片
	 * @param lines 需要显示的文字行列表
	 * @param font 需要显示成的字体
	 * @param fgColor 文字颜色
	 * @param bgColor 背景颜色
	 * @param padding 文字与边框的距离
	 * @param lineInterval 每行文字的间隔
	 * @return
	 */
	public static BufferedImage newTextImage(List<String> lines, Font font, Color fgColor, Color bgColor, int padding,
		int lineInterval) {
		Graphics tmp = newImage(1, 1).createGraphics();
		tmp.setFont(font);
		tmp.setColor(fgColor);
		int lineHeight = ImageTextUtil.getTextHeight(tmp);
		int totalHeight = lines.size() * lineHeight + 2 * padding + (lines.size() - 1) * lineInterval;
		int maxWidth = 0;
		for (String line : lines) {
			maxWidth = Integer.max(maxWidth, ImageTextUtil.getTextWidth(line, tmp));
		}
		maxWidth += 2 * padding;
		BufferedImage img = bgColor == null ? ImageUtil.newImage(maxWidth, totalHeight)
			: ImageUtil.newImage(bgColor, maxWidth, totalHeight);
		Graphics g = img.createGraphics();
		g.setFont(font);
		g.setColor(fgColor);
		int nowY = padding - 1 + lineHeight - 1;
		for (String line : lines) {
			int lineWidth = ImageTextUtil.getTextWidth(line, g);
			g.drawString(line, padding + (maxWidth / 2) - (lineWidth / 2), nowY);
			nowY += lineHeight + lineInterval;
		}
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
	 * 使用指定画板绘制绕中心旋转指定角度的图片
	 * @param g 使用的画板
	 * @param img 绘制的图片
	 * @param x 旋转前的 x 坐标
	 * @param y 旋转前的 y 坐标
	 * @param width 绘制的宽度
	 * @param height 绘制的高度
	 * @param radian 旋转的弧度
	 */
	public static void paint(Graphics2D g, BufferedImage img, int x, int y, int width, int height, double radian) {
		int centerX = x + width / 2;
		int centerY = y + height / 2;
		g.translate(centerX, centerY);
		g.rotate(radian);
		g.drawImage(img, -width / 2, -height / 2, width, height, null);
		g.rotate(-radian);
		g.translate(-centerX, -centerY);
	}

	/**
	 * 获取指定图片水平翻转后的新图片
	 * @param img
	 * @return
	 */
	public static BufferedImage horizontalFlip(BufferedImage img) {
		return transform(new AffineTransform(-1, 0, 0, 1, img.getWidth() - 1, 0), img);
	}

	/**
	 * 获取指定图片垂直翻转后的新图片
	 * @param img
	 * @return
	 */
	public static BufferedImage verticalFlip(BufferedImage img) {
		return transform(new AffineTransform(1, 0, 0, -1, 0, img.getHeight() - 1), img);
	}

	/**
	 * 获取 img 使用 transform 映射后的图片
	 * @param transform
	 * @param img
	 * @return
	 */
	private static BufferedImage transform(AffineTransform transform, BufferedImage img) {
		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
		return op.filter(img, null);
	}

	/**
	 * 修改 ARGB 图片透明度，并返回修改后的·原图片
	 * @param img 需要修改透明度的图片
	 * @param transparency 需要为的透明度(0完全透明~255不透明)
	 * @return
	 */
	public static BufferedImage transparent(BufferedImage img, int transparency) {
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				// 0x00FFFFFF == AARRGGBB
				img.setRGB(i, j, (img.getRGB(i, j) & 0x00FFFFFF) | transparency << 24);
			}
		}
		return img;
	}

}
