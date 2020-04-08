package cn.milai.ib.container.form;

import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import com.google.common.collect.Lists;

import cn.milai.ib.IBObject;
import cn.milai.ib.character.Controllable;
import cn.milai.ib.container.CharacterAwareContainer;
import cn.milai.ib.container.Image;
import cn.milai.ib.container.listener.ContainerEventListener;
import cn.milai.ib.ex.IBContainerException;

/**
 * FormContainer 抽象基类
 * @author milai
 */
public abstract class AbstractFormContainer extends CharacterAwareContainer implements FormContainer {

	private JFrame form;
	private List<Controllable> controllables;
	private Image bgImage;

	@SuppressWarnings("serial")
	public AbstractFormContainer() {
		resetCharacterAwareContainer();
		form = new JFrame() {
			@Override
			public final void paint(Graphics g) {
				paintContainer(g);
			}
		};
		initForm(form);
		form.setLocationRelativeTo(null);
		form.setLayout(null);
		form.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		form.setResizable(false);
		form.addMouseListener(new MouseEventDispatcher(this));
		form.addKeyListener(new KeyEventDispatcher(this));
		form.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				close();
			}
		});
	}

	@Override
	protected void resetCharacterAwareContainer() {
		controllables = Lists.newArrayList();
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

	/**
	 * 获取持有的窗口
	 * @return
	 */
	public JFrame getForm() {
		return form;
	}

	@Override
	public int getWidth() {
		return form.getWidth();
	}

	@Override
	public int getHeight() {
		return form.getHeight();
	}

	@Override
	public int getContentHeight() {
		return form.getContentPane().getHeight();
	}

	@Override
	public void resize(int width, int height) {
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

	/**
	 * 绘制容器
	 * @param g
	 */
	protected void paintContainer(Graphics g) {
		BufferedImage image = (BufferedImage) form.createImage(getWidth(), getHeight());
		Graphics buffer = image.getGraphics();
		if (bgImage != null) {
			buffer.drawImage(bgImage.next(), 0, 0, getWidth(), getHeight(), null);
		}
		List<? extends IBObject> objs = getAll(IBObject.class);
		Collections.sort(objs);
		for (IBObject o : objs) {
			o.paintWith(buffer);
		}
		buffer.dispose();
		g.drawImage(image, 0, 0, null);
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

	/**
	 * 创建并设置好 form 后调用
	 * @param form 创建的 form
	 */
	protected abstract void initForm(JFrame form);
}
