package cn.milai.ib.container.plugin.ui.form;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

/**
 * 去除边框并实现拖拽功能的 {@link JFrame}
 * @author milai
 * @date 2021.03.23
 */
public abstract class UndecoratedForm extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 可调节窗口大小的边界区域宽度
	 */
	private static final int BORDER = 5;

	/**
	 * 最后一次鼠标按下时的 X 坐标
	 */
	private int lastX;

	/**
	 * 最后一次鼠标按下时的 Y 坐标
	 */
	private int lastY;

	private DraggleHandler draggler;

	private DraggleHandler leftDraggle = (x, y) -> {
		int w = getWidth() + lastX - x;
		int h = getHeight();
		setBounds(x, getY(), w, h);
	};

	private DraggleHandler rightDraggle = (x, y) -> {
		int w = x - getX();
		setBounds(getX(), getY(), w, getHeight());
	};

	private DraggleHandler topDraggle = (x, y) -> {
		int h = getHeight() + getY() - y;
		setBounds(getX(), y, getWidth(), h);
	};

	private DraggleHandler bottomDraggle = (x, y) -> {
		int h = y - getY();
		setBounds(getX(), getY(), getWidth(), h);
	};

	private DraggleHandler leftTopDraggle = (x, y) -> {
		int w = getWidth() + lastX - x;
		int h = getHeight() + lastY - y;
		setBounds(x, y, w, h);
	};

	private DraggleHandler leftBottomDraggle = (x, y) -> {
		int w = getWidth() + lastX - x;
		int h = getHeight() + y - lastY;
		setBounds(x, getY(), w, h);
	};

	private DraggleHandler rightTopDraggle = (x, y) -> {
		int w = x - getX();
		int h = getHeight() + getY() - y;
		setBounds(getX(), y, w, h);
	};

	private DraggleHandler rightBottomDraggle = (x, y) -> {
		int w = x - getX();
		int h = y - getY();
		setBounds(getX(), getY(), w, h);
	};

	private DraggleHandler moveDraggle = (x, y) -> {
		setLocation(getX() + x - lastX, getY() + y - lastY);
	};

	public UndecoratedForm() {
		setLocationRelativeTo(null);
		setLayout(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setUndecorated(true);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				lastX = e.getXOnScreen();
				lastY = e.getYOnScreen();
			}
		});
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				draggler.onDraggle(x, y);
				lastX = x;
				lastY = y;
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				lastX = e.getXOnScreen();
				lastY = e.getYOnScreen();
				int cursorType = Cursor.DEFAULT_CURSOR;
				if (inLeftBorder()) {
					if (inTopBorder()) {
						cursorType = Cursor.NW_RESIZE_CURSOR;
						draggler = leftTopDraggle;
					} else if (inBottonBorder()) {
						cursorType = Cursor.SW_RESIZE_CURSOR;
						draggler = leftBottomDraggle;
					} else {
						cursorType = Cursor.W_RESIZE_CURSOR;
						draggler = leftDraggle;
					}
				} else if (inRightBorder()) {
					if (inTopBorder()) {
						cursorType = Cursor.NE_RESIZE_CURSOR;
						draggler = rightTopDraggle;
					} else if (inBottonBorder()) {
						cursorType = Cursor.SE_RESIZE_CURSOR;
						draggler = rightBottomDraggle;
					} else {
						cursorType = Cursor.E_RESIZE_CURSOR;
						draggler = rightDraggle;
					}
				} else if (inTopBorder()) {
					cursorType = Cursor.N_RESIZE_CURSOR;
					draggler = topDraggle;
				} else if (inBottonBorder()) {
					cursorType = Cursor.S_RESIZE_CURSOR;
					draggler = bottomDraggle;
				} else if (inMoveable(e.getX(), e.getY())) {
					cursorType = Cursor.MOVE_CURSOR;
					draggler = moveDraggle;
				}
				setCursor(new Cursor(cursorType));
			}
		});
	}

	private boolean inBottonBorder() {
		return between(lastY, getY() + getHeight(), BORDER);
	}

	private boolean inTopBorder() {
		return between(lastY, getY(), BORDER);
	}

	private boolean inRightBorder() {
		return between(lastX, getX() + getWidth(), BORDER);
	}

	private boolean inLeftBorder() {
		return between(lastX, getX(), BORDER);
	}

	private boolean between(int v, int mid, int delta) {
		return v >= mid - delta && v <= mid + delta;
	}

	/**
	 * 判断指定点(相对于窗口左上角的坐标)是否处于可拖动窗口的区域
	 * @param x
	 * @param y
	 * @return
	 */
	protected abstract boolean inMoveable(int x, int y);

	/**
	 * 在 {@link JFrame} 上拖动的处理器
	 * @author milai
	 * @date 2021.03.23
	 */
	private interface DraggleHandler {
		/**
		 * 在窗口上拖动时调用。
		 * {@link UndecoratedForm#lastX} 和 {@link UndecoratedForm#lastY} 将在该方法调用后更新为参数里的 {@code x} 和 {@code y} 
		 * @param x 拖动后光标在屏幕上的绝对 X 坐标
		 * @param y 拖动后光标在屏幕上的绝对 Y 坐标
		 */
		void onDraggle(int x, int y);
	}

}
