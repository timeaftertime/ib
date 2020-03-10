package cn.milai.ib.component.form.text;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import cn.milai.ib.component.form.WaitNextPageTip;
import cn.milai.ib.container.form.FormContainer;
import cn.milai.ib.container.form.listener.Command;
import cn.milai.ib.container.form.listener.KeyShield;
import cn.milai.ib.container.form.listener.KeyboardListener;
import cn.milai.ib.loader.ImageLoader;
import cn.milai.ib.util.ImageTextUtil;
import cn.milai.ib.util.ImageUtil;

/**
 * 显示剧情文字的对话框
 * @author milai
 */
public class DramaDialog extends AbstractTextFormComponent {

	private static final String SAMPLE_STR = "字";

	private static final String P_HOR_MARGIN = "horMargin";
	private static final String P_VER_MARGIN = "verMargin";
	private static final String P_KEY_WAIT_WIDTH = "waitWidth";
	private static final String P_KEY_WAIT_HEIGHT = "waitHeight";
	private static final String P_SPEAKER_WIDTH = "speakerWidth";
	private static final String P_SPEAKER_HEIGHT = "speakerHeight";

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
	private static Image waitNextPage = ImageLoader.load(WaitNextPageTip.class);

	private int horMargin;
	private int verMargin;
	private int waitWidth;
	private int waitHeight;

	private String text;
	private int readIndex;

	private Image speaker;
	private int speakerWidth;
	private int speakerHeight;

	public DramaDialog(int x, int y, Image speaker, String text, FormContainer container) {
		super(x, y, container);

		horMargin = proratedIntProp(P_HOR_MARGIN);
		verMargin = proratedIntProp(P_VER_MARGIN);
		waitWidth = proratedIntProp(P_KEY_WAIT_WIDTH);
		waitHeight = proratedIntProp(P_KEY_WAIT_HEIGHT);
		speakerWidth = proratedIntProp(P_SPEAKER_WIDTH);
		speakerHeight = proratedIntProp(P_SPEAKER_HEIGHT);
		this.text = text;
		readIndex = 0;
		this.speaker = speaker;
		pageDown();

		getContainer().setPin(true);
		getContainer().addKeyboardListener(nextPageListener);
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
		BufferedImage img = ImageUtil.newImage(getWidth(), getHeight());
		Graphics g = initNewGraphics(img);
		render(g);
		g.dispose();
		return img;
	}

	/**
	 * 获取一个新的初始化好的画板 (Graphics 实例)
	 * @return
	 */
	private Graphics initNewGraphics(Image image) {
		Graphics g = image.getGraphics();
		g.setFont(getTextFont());
		g.setColor(getTextColor());
		return g;
	}

	/**
	 * 使用 Graphics 渲染出说话者当前的剧本文字及边框
	 * @param g
	 */
	private void render(Graphics g) {
		// 边框
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		// 文字 
		int textHeight = ImageTextUtil.getTextHeight(g);
		int nowBottom = verMargin + textHeight + speakerHeight;
		// 至少绘制一行
		int limitBottom = Integer.max(getHeight() - verMargin, nowBottom + textHeight);
		while (readIndex < text.length() && (nowBottom + textHeight <= limitBottom)) {
			String nextLine = nextLine(g);
			g.drawString(nextLine, horMargin, nowBottom);
			nowBottom += textHeight;
		}
	}

	private String nextLine(Graphics g) {
		// 确保一行至少一个字符
		int widthLimit = Integer.max(
			// 有待绘字符才调用该方法，所以这里直接 charAt() 不会有问题
			ImageTextUtil.getTextWidth(SAMPLE_STR + text.charAt(readIndex), g),
			getWidth() - 2 * horMargin);
		StringBuilder sb = new StringBuilder();
		for (; readIndex < text.length(); readIndex++) {
			char ch = text.charAt(readIndex);
			if (ch == '\n') {
				readIndex++;
				return sb.toString();
			}
			if (ImageTextUtil.getTextWidth(sb.toString() + ch, g) > widthLimit) {
				return sb.toString();
			}
			sb.append(ch);
		}
		return sb.toString();
	}

	@Override
	public Image getImage() {
		BufferedImage img = ImageUtil.copy(textImage);
		Graphics2D g = img.createGraphics();
		// 说话者头像
		if (speaker != null) {
			g.drawRect(0, 0, speakerWidth, speakerHeight);
			g.drawImage(speaker, 0, 0, speakerWidth, speakerHeight, null);
		}
		// 表示还有下一页的箭头
		if (readIndex < text.length()) {
			g.drawImage(waitNextPage, getWidth() - waitWidth, getHeight() - waitHeight, waitWidth, waitHeight, null);
		}
		return img;
	}

}
