package cn.milai.ib.container.plugin.ui.screen.form;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

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

	private DraggleHandler handler;

	private DraggleHandler leftDraggle = (x, y) -> {
		int w = getWidth() + getX() - x;
		rebounds(getMX() - w, getY(), w, getHeight());
	};

	private DraggleHandler rightDraggle = (x, y) -> {
		rebounds(getX(), getY(), x - getX(), getHeight());
	};

	private DraggleHandler topDraggle = (x, y) -> {
		int h = getHeight() + getY() - y;
		rebounds(getX(), getMY() - h, getWidth(), h);
	};

	private DraggleHandler bottomDraggle = (x, y) -> {
		rebounds(getX(), getY(), getWidth(), y - getY());
	};

	private DraggleHandler leftTopDraggle = (x, y) -> {
		int w = getWidth() + getX() - x;
		int h = getHeight() + getY() - y;
		rebounds(getMX() - w, getMY() - h, w, h);
	};

	private DraggleHandler leftBottomDraggle = (x, y) -> {
		int w = getWidth() + getX() - x;
		int h = getHeight() + y - getMY();
		rebounds(getMX() - w, getY(), w, h);
	};

	private DraggleHandler rightTopDraggle = (x, y) -> {
		int w = x - getX();
		int h = getHeight() + getY() - y;
		rebounds(getX(), getMY() - h, w, h);
	};

	private DraggleHandler rightBottomDraggle = (x, y) -> {
		int w = x - getX();
		int h = y - getY();
		rebounds(getX(), getY(), w, h);
	};

	private DraggleHandler moveDraggle = (x, y) -> {
		setLocation(getX() + x - lastX, getY() + y - lastY);
	};

	private final Map<Integer, DraggleHandler> cursorDraggler = new HashMap<Integer, DraggleHandler>() {
		private static final long serialVersionUID = 1L;
		{
			put(Cursor.NW_RESIZE_CURSOR, leftTopDraggle);
			put(Cursor.SW_RESIZE_CURSOR, leftBottomDraggle);
			put(Cursor.W_RESIZE_CURSOR, leftDraggle);
			put(Cursor.NE_RESIZE_CURSOR, rightTopDraggle);
			put(Cursor.SE_RESIZE_CURSOR, rightBottomDraggle);
			put(Cursor.E_RESIZE_CURSOR, rightDraggle);
			put(Cursor.N_RESIZE_CURSOR, topDraggle);
			put(Cursor.S_RESIZE_CURSOR, bottomDraggle);
			put(Cursor.MOVE_CURSOR, moveDraggle);
		}
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
				handler = cursorDraggler.get(cursorOf(lastX, lastY));
			}
		});

		addMouseMotionListener(new MouseAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				if (handler != null) {
					handler.onDraggle(x, y);
				}
				lastX = x;
				lastY = y;
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				lastX = e.getXOnScreen();
				lastY = e.getYOnScreen();
				setCursor(new Cursor(cursorOf(lastX, lastY)));
			}
		});
	}

	protected int getMX() { return getX() + getWidth(); }

	protected int getMY() { return getY() + getHeight(); }

	/**
	 * 获取光标位于指定位置时应该展示的光标类型
	 * @param x
	 * @param y
	 * @return
	 */
	private int cursorOf(int x, int y) {
		int cursorType = Cursor.DEFAULT_CURSOR;
		if (inLeftBorder(x)) {
			if (inTopBorder(y)) {
				cursorType = Cursor.NW_RESIZE_CURSOR;
			} else if (inBottonBorder(y)) {
				cursorType = Cursor.SW_RESIZE_CURSOR;
			} else {
				cursorType = Cursor.W_RESIZE_CURSOR;
			}
		} else if (inRightBorder(x)) {
			if (inTopBorder(y)) {
				cursorType = Cursor.NE_RESIZE_CURSOR;
			} else if (inBottonBorder(y)) {
				cursorType = Cursor.SE_RESIZE_CURSOR;
			} else {
				cursorType = Cursor.E_RESIZE_CURSOR;
			}
		} else if (inTopBorder(y)) {
			cursorType = Cursor.N_RESIZE_CURSOR;
		} else if (inBottonBorder(y)) {
			cursorType = Cursor.S_RESIZE_CURSOR;
		} else if (inMoveable(x - getX(), y - getY())) {
			cursorType = Cursor.MOVE_CURSOR;
		}
		return cursorType;
	}

	private boolean inBottonBorder(int y) {
		return between(y, getMY(), BORDER);
	}

	private boolean inTopBorder(int y) {
		return between(y, getY(), BORDER);
	}

	private boolean inRightBorder(int x) {
		return between(x, getMX(), BORDER);
	}

	private boolean inLeftBorder(int x) {
		return between(x, getX(), BORDER);
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

	private void rebounds(int x, int y, int w, int h) {
		doRebounds(getX(), getY(), getWidth(), getHeight(), x, y, Math.max(w, 0), Math.max(h, 0));
	}

	/**
	 * 重新设置当前 bounds 为指定值
	 * @param preX 当前 X
	 * @param preY 当前 Y
	 * @param preW 当前 W
	 * @param preH 当前 H
	 * @param x 要设置的 X
	 * @param y 要设置的 Y
	 * @param w 要设置的 W
	 * @param h 要设置的 H
	 */
	protected void doRebounds(int preX, int preY, int preW, int preH, int x, int y, int w, int h) {
		setBounds(x, y, w, h);
	}

	@Override
	public void setSize(int width, int height) {
		rebounds(getX(), getY(), width, height);
	}

	@Override
	public void setLocation(int x, int y) {
		rebounds(x, y, getWidth(), getHeight());
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		rebounds(x, y, width, height);
	}

	/**
	 * 直接设置范围
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	protected void simpleRebounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
	}

}
