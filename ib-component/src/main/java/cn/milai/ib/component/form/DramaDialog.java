package cn.milai.ib.component.form;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import cn.milai.ib.container.Container;
import cn.milai.ib.interaction.form.FormContainer;
import cn.milai.ib.interaction.form.IBFormComponent;
import cn.milai.ib.interaction.form.listener.Command;
import cn.milai.ib.interaction.form.listener.KeyShield;
import cn.milai.ib.interaction.form.listener.KeyboardListener;
import cn.milai.ib.util.ImageUtil;

public class DramaDialog extends IBFormComponent {

	private static final String TEXT_SIZE = "textSize";
	private static final String TEXT_FONT = "textFont";
	private static final String HOR_MARGIN = "horMargin";
	private static final String VER_MARGIN = "verMargin";
	private static final String TEXT_COLOR = "textColor";
	private static final String KEY_WAIT_WIDTH = "waitWidth";
	private static final String KEY_WAIT_HEIGHT = "waitHeight";

	private final KeyboardListener nextPageListener = new KeyShield(new KeyboardListener() {
		@Override
		public boolean keyUp(Command e) {
			return false;
		}

		@Override
		public boolean keyDown(Command e) {
			if (e == Command.SHOOT) {
				pageDown();
			}
			return false;
		}
	});

	private BufferedImage textImage;
	private Image waitNextPage = ImageUtil.loadImage(DramaDialog.class.getResource("/img/component/wait_next_page.gif"));

	private int textSize;
	private String textFont;
	private int horMargin;
	private int verMargin;
	private Color color;
	private int waitWidth;
	private int waitHeight;

	private String text;
	private int readIndex;
	private Font font;

	private Image speaker;

	public DramaDialog(int x, int y, Image speaker, String text, Container container) throws ClassNotFoundException {
		super(x, y, container);
		textSize = proratedIntProp(TEXT_SIZE);
		textFont = prop(TEXT_FONT);
		font = new Font(textFont, Font.BOLD, textSize);
		horMargin = proratedIntProp(HOR_MARGIN);
		verMargin = proratedIntProp(VER_MARGIN);
		color = parseColor(intProp(TEXT_COLOR));
		waitWidth = proratedIntProp(KEY_WAIT_WIDTH);
		waitHeight = proratedIntProp(KEY_WAIT_HEIGHT);
		this.text = text;
		readIndex = 0;
		this.speaker = speaker;
		pageDown();

		getContainer().setPin(true);
		getContainer().addKeyboardListener(nextPageListener);
	}

	private static Color parseColor(int color) {
		return new Color((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF);
	}

	/**
	 * 设置当前显示的文本为下一页
	 */
	private void pageDown() {
		if (readIndex >= text.length()) {
			FormContainer container = getContainer();
			container.removeKeyboardListener(nextPageListener);
			container.removeObject(this);
			container.setPin(false);
			return;
		}
		textImage = createNextTextImage();
	}

	/**
	 * 创建下一张只包含文字的图片
	 */
	private BufferedImage createNextTextImage() {
		BufferedImage img = createTransaparentImage();
		Graphics g = initNewGraphics(img);
		render(g);
		return img;
	}

	/**
	 * 获取一个新的初始化好的画板 (Graphics 实例)
	 * @return
	 */
	private Graphics initNewGraphics(Image image) {
		Graphics g = image.getGraphics();
		g.setFont(font);
		g.setColor(color);
		return g;
	}

	/**
	 * 构造背景透明的图片
	 * @return
	 */
	private BufferedImage createTransaparentImage() {
		Graphics2D g2d = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB).createGraphics();
		BufferedImage img = g2d.getDeviceConfiguration().createCompatibleImage(getWidth(), getHeight(), Transparency.TRANSLUCENT);
		g2d.dispose();
		return img;
	}

	/**
	 * 使用 Graphics 渲染出说话者当前的剧本文字及边框
	 * @param g
	 */
	private void render(Graphics g) {
		// 边框
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		// 文字
		int bottom = verMargin;
		while (readIndex < text.length() && (bottom + g.getFont().getSize() < getHeight() - horMargin)) {
			String nextLine = nextLine(g.getFont().getSize());
			g.drawString(nextLine, horMargin, bottom);
			bottom += g.getFont().getSize();
		}
	}

	private String nextLine(int fontSize) {
		// Integer.max 确保一行至少一个字符
		int relativeWidth = Integer.max(2, (getWidth() - 2 * horMargin) / fontSize * 2);
		StringBuilder sb = new StringBuilder();
		int right = 0;
		for (; readIndex < text.length(); readIndex++) {
			char ch = text.charAt(readIndex);
			right += widthOf(ch);
			if (ch == '\n') {
				readIndex++;
				return sb.toString();
			}
			if (right > relativeWidth) {
				return sb.toString();
			}
			sb.append(ch);
		}
		return sb.toString();
	}

	private int widthOf(char ch) {
		if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
			return 1;
		}
		return 2;
	}

	@Override
	public Image getImage() {
		BufferedImage img = ImageUtil.copy(textImage);
		Graphics2D g = img.createGraphics();
		// 说话者头像
		if (speaker != null) {
			g.drawRect(0, 0, 60, 60);
			g.drawImage(speaker, 0, 0, 60, 60, null);
		}
		// 表示还有下一页的箭头
		if (readIndex < text.length()) {
			g.drawImage(waitNextPage, getWidth() - waitWidth, getHeight() - waitHeight, waitWidth, waitHeight, null);
		}
		return img;
	}

	@Override
	public void setImage(Image img) {
		throw new UnsupportedOperationException();
	}

}
