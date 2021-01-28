package cn.milai.ib.container.ui.form;

import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JFrame;

import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import com.google.common.collect.Lists;

import cn.milai.ib.IBObject;
import cn.milai.ib.container.AbstractDramaContainer;
import cn.milai.ib.container.ContainerEventListener;
import cn.milai.ib.container.IBContainerException;
import cn.milai.ib.container.control.Controllable;
import cn.milai.ib.container.ui.BaseCamera;
import cn.milai.ib.container.ui.Camera;
import cn.milai.ib.container.ui.Image;
import cn.milai.ib.util.ImageUtil;

/**
 * {@link FormContainer} 抽象基类
 * @author milai
 */
public abstract class AbstractFormContainer extends AbstractDramaContainer implements FormContainer {

	private JFrame form;
	private List<Controllable> controllables;
	private Image bgImage;
	private Camera camera;
	private BufferedImage ui;

	@SuppressWarnings("serial")
	public AbstractFormContainer() {
		form = new JFrame() {
			@Override
			public final void paint(Graphics g) {
				paintContainer(g);
			}
		};
		initForm();
		form.setLocationRelativeTo(null);
		form.setLayout(null);
		form.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		form.addMouseListener(new MouseEventDispatcher(this));
		form.addKeyListener(new KeyEventDispatcher(this));
		form.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				close();
			}
		});
		ui = ImageUtil.newImage(1, 1);
		resetCharacterAwareContainer();
	}

	@Override
	protected void resetCharacterAwareContainer() {
		controllables = Lists.newArrayList();
		camera = new BaseCamera();
		addEventListener(new ContainerEventListener() {
			@Override
			public void onObjectAdded(IBObject obj) {
				synchronized (controllables) {
					if (obj instanceof Controllable) {
						controllables.add((Controllable) obj);
						AnnotationAwareOrderComparator.sort(controllables);
					}
				}
			}

			@Override
			public void onObjectRemoved(IBObject obj) {
				synchronized (controllables) {
					controllables.remove(obj);
				}
			}
		});
	}

	/**
	 * 获取容器中所有 Controllable 实例
	 * @return
	 */
	public List<Controllable> getControllables() {
		return controllables;
	}

	@Override
	public JFrame getForm() {
		return form;
	}

	@Override
	public int getUIWidth() {
		return form.getWidth();
	}

	@Override
	public int getUIHeight() {
		return form.getHeight();
	}

	@Override
	public int getUICHeight() {
		return form.getContentPane().getHeight();
	}

	@Override
	public void resizeUI(int width, int height) {
		form.setSize(width, height);
		form.setLocationRelativeTo(null);
	}

	@Override
	public String getTitle() {
		return form.getTitle();
	}

	@Override
	public void setTitle(String title) {
		form.setTitle(title);
	}

	@Override
	public void setBackgroud(Image img) throws IBContainerException {
		this.bgImage = img;
	}

	@Override
	protected void repaintContainer() {
		form.repaint();
	}

	@Override
	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	@Override
	public Camera getCamera() {
		return camera;
	}

	/**
	 * 绘制容器
	 * @param g
	 */
	protected void paintContainer(Graphics g) {
		JFrame form = getForm();
		int width = getWidth();
		int height = getHeight();
		BufferedImage image = (BufferedImage) form.createImage(width, height);
		Graphics buffer = image.getGraphics();
		if (bgImage != null) {
			buffer.drawImage(bgImage.next(), 0, 0, width, height, null);
		}
		List<? extends IBObject> objs = getAll(IBObject.class);
		Collections.sort(objs, Comparator.comparingInt(IBObject::getZ));
		for (IBObject o : objs) {
			o.paintWith(buffer);
		}
		buffer.dispose();
		g.drawImage(camera.reflect(image), 0, 0, getUIWidth(), getUIHeight(), null);
	}

	@Override
	protected void startContainer() {
		form.setVisible(true);
	}

	@Override
	protected void closeContainer() {
		form.setVisible(false);
		form.dispose();
	}

	@Override
	public BufferedImage getUI() {
		return ui;
	}

	/**
	 * 创建 form 后调用
	 */
	protected abstract void initForm();
}
