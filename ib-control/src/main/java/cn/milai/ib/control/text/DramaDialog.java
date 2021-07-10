package cn.milai.ib.control.text;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.annotation.Order;

import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.plugin.control.cmd.Cmd;
import cn.milai.ib.container.plugin.control.cmd.PointCmd;
import cn.milai.ib.container.plugin.ui.Image;
import cn.milai.ib.control.WaitNextPageTip;
import cn.milai.ib.graphics.Images;
import cn.milai.ib.graphics.Texts;
import cn.milai.ib.item.BasePainter;
import cn.milai.ib.item.Controllable;
import cn.milai.ib.item.property.Painter;

/**
 * 显示剧情文字的对话框
 * @author milai
 */
@Order(-100)
public class DramaDialog extends AbstractTextControl implements Controllable {

	private static final String SAMPLE_STR = "字";

	public static final String PARAM_TEXT = "text";
	public static final String PARAM_SPEAKER_IMG = "speakerImg";
	public static final String PARAM_SPEAKER_NAME = "speakerName";

	private BufferedImage baseImage;
	private static WaitNextPageTip waitNextPage;

	private int horMargin = 17;
	private int verMargin = 7;

	private String text;
	private int readIndex;

	private BufferedImage speakerImg;
	private String speakerName;
	private int speakerWidth = 70;
	private int speakerHeight = 70;

	public DramaDialog(int x, int y, Image speaker, String text) {
		this(asParams(text, speaker, null));
	}

	public DramaDialog(Map<String, Object> params) {
		this.text = (String) params.get(PARAM_TEXT);
		readIndex = 0;
		Image image = (Image) params.get(PARAM_SPEAKER_IMG);
		if (image != null) {
			this.speakerImg = image.first();
		}
		this.speakerName = (String) params.get(PARAM_SPEAKER_NAME);
	}

	public static Map<String, Object> asParams(String text, Image speakerImg, String speakerName) {
		Map<String, Object> m = new HashMap<>();
		m.put(PARAM_TEXT, text == null ? "" : text);
		m.put(PARAM_SPEAKER_IMG, speakerImg);
		m.put(PARAM_SPEAKER_NAME, speakerName == null ? "" : speakerName);
		return m;
	}

	@Override
	protected void initItem() {
		waitNextPage = new WaitNextPageTip();
		waitNextPage.setW(getW() / 18);
		waitNextPage.setH(getH() / 10);
		waitNextPage.init(container());
		pageDown();
		((LifecycleContainer) container()).setPined(true);
	}

	/**
	 * 设置当前显示的文本为下一页
	 */
	private void pageDown() {
		if (readIndex >= text.length()) {
			LifecycleContainer container = (LifecycleContainer) container();
			container.removeObject(this);
			container.setPined(false);
			return;
		}
		baseImage = createNextBaseImage();
	}

	/**
	 * 创建下一张只包含文字说话者头像的图片
	 */
	private BufferedImage createNextBaseImage() {
		BufferedImage img = Images.newImage(getIntW(), getIntH());
		Graphics g = initNewGraphics(img);
		render(g);
		g.dispose();
		return img;
	}

	/**
	 * 获取一个新的初始化好的画板 (Graphics 实例)
	 * @return
	 */
	private Graphics initNewGraphics(BufferedImage image) {
		Graphics g = image.getGraphics();
		g.setFont(getTextProperty().getFont());
		g.setColor(getTextProperty().getColor());
		return g;
	}

	/**
	 * 使用 Graphics 渲染出说话者当前的剧本文字及边框
	 * @param g
	 */
	private void render(Graphics g) {
		// 边框
		g.drawRect(0, 0, getIntW() - 1, getIntH() - 1);
		// 说话者头像
		if (speakerImg != null) {
			g.drawRect(0, 0, speakerWidth, speakerHeight);
			g.drawImage(speakerImg, 0, 0, speakerWidth, speakerHeight, null);
		}
		// 说话者名字
		if (speakerName != null) {
			g.drawString(speakerName, horMargin + speakerWidth, speakerHeight / 2);
		}
		// 说话内容
		int textHeight = Texts.getTextHeight(g);
		int nowBottom = verMargin + textHeight + speakerHeight;
		// 至少绘制一行
		int limitBottom = Integer.max(getIntH() - verMargin, nowBottom + textHeight);
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
			Texts.getTextWidth(SAMPLE_STR + text.charAt(readIndex), g),
			getIntW() - 2 * horMargin
		);
		StringBuilder sb = new StringBuilder();
		for (; readIndex < text.length(); readIndex++) {
			char ch = text.charAt(readIndex);
			if (ch == '\n') {
				readIndex++;
				return sb.toString();
			}
			if (Texts.getTextWidth(sb.toString() + ch, g) > widthLimit) {
				return sb.toString();
			}
			sb.append(ch);
		}
		return sb.toString();
	}

	@Override
	protected Painter createPainter() {
		return new BasePainter() {
			@Override
			public BufferedImage getNowImage() {
				BufferedImage img = Images.copy(baseImage);
				Graphics2D g = img.createGraphics();
				// 表示还有下一页的箭头
				if (readIndex < text.length()) {
					g.drawImage(
						waitNextPage.getPainter().getNowImage(),
						getIntW() - waitNextPage.getIntW(),
						getIntH() - waitNextPage.getIntH(),
						waitNextPage.getIntW(),
						waitNextPage.getIntH(),
						null
					);
				}
				return img;
			}
		};
	}

	@Override
	public boolean exec(Cmd cmd) {
		if (isPageDownCmd(cmd)) {
			pageDown();
		}
		return false;
	}

	protected boolean isPageDownCmd(Cmd cmd) {
		if (cmd.getType() != Cmd.CLICK) {
			return false;
		}
		if (!(cmd instanceof PointCmd)) {
			return false;
		}
		PointCmd c = (PointCmd) cmd;
		return containsPoint(c.getX(), c.getY());
	}

}
