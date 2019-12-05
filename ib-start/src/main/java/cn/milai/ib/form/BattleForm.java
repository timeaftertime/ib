package cn.milai.ib.form;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

import cn.milai.ib.conf.ImageConf;
import cn.milai.ib.conf.KeyConf;
import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.container.listener.GameEventListener;
import cn.milai.ib.container.listener.RefreshListener;
import cn.milai.ib.form.listener.KeyboardListener;
import cn.milai.ib.obj.IBObject;
import cn.milai.ib.obj.character.IBCharacter;
import cn.milai.ib.property.Alive;
import cn.milai.ib.property.CanCrash;
import cn.milai.ib.property.CanCrashed;
import cn.milai.ib.property.Movable;
import cn.milai.ib.property.Paintable;

/**
 * 战斗场景窗体类
 * 
 * @author milai
 *
 */
public class BattleForm extends DoubleBufferForm implements FormContainer {

	private static final long serialVersionUID = 1L;

	/**
	 * 每帧的实际时间间隔
	 */
	private static final long MILLISEC_PER_FRAME = SystemConf.frameProrate(50L);

	private static final String TITLE = "敌星弹雨";

	private static final int WIDTH = SystemConf.prorate(792);
	private static final int HEIGHT = SystemConf.prorate(984);

	/**
	 * 从当前窗体启动到现在刷新的帧数
	 */
	private volatile long frame = 0;

	private Image bgImage;
	private List<IBObject> gameObjs;
	private List<Paintable> paintables;
	private List<Movable> movables;
	private List<Alive> alives;
	private List<CanCrash> canCrashs;
	private List<CanCrashed> canCrasheds;
	private List<KeyboardListener> keyboardListeners;
	private List<GameEventListener> gameEventListeners;
	private List<RefreshListener> refreshListener;

	private Thread refreshThread;

	private boolean gameOver;
	private boolean closed;

	public void setGameOver() {
		gameOver = true;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public BattleForm() {
		init();
	}

	public void init() {
		this.setTitle(TITLE);
		this.setSize(WIDTH, HEIGHT);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);

		bgImage = ImageConf.BATTLE_BG;
		gameObjs = new ArrayList<>();
		paintables = new ArrayList<Paintable>();
		movables = new ArrayList<Movable>();
		alives = new ArrayList<Alive>();
		canCrashs = new ArrayList<CanCrash>();
		canCrasheds = new ArrayList<CanCrashed>();
		keyboardListeners = new ArrayList<KeyboardListener>();
		gameEventListeners = new ArrayList<GameEventListener>();
		refreshListener = new ArrayList<RefreshListener>();
		gameOver = false;
		closed = false;

		// 窗口关闭监听
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
				for (GameEventListener listener : new ArrayList<>(gameEventListeners)) {
					listener.onFormClosed();
				}
				closed = true;
			}
		});

		// 用于刷新的线程
		refreshThread = new RefreshThread();

		// 按键监听器
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				Command action = KeyConf.commandOf(e.getKeyCode());
				if (action == null) {
					return;
				}
				for (KeyboardListener listener : keyboardListeners) {
					listener.keyDown(action);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				Command action = KeyConf.commandOf(e.getKeyCode());
				if (action == null) {
					return;
				}
				for (KeyboardListener listener : keyboardListeners) {
					listener.keyUp(action);
				}
			}
		});
	}

	private void checkCrashed() {
		for (CanCrash crash : new ArrayList<>(canCrashs)) {
			for (CanCrashed crashed : new ArrayList<>(canCrasheds)) {
				if (crash.sameCamp(crashed)) {
					continue;
				}
				if (((IBCharacter) crash).isContactWith((IBCharacter) crashed)) {
					crash.onCrash(crashed);
				}
			}
		}
	}

	private class RefreshThread extends Thread {
		@Override
		public void run() {
			while (!closed) {
				moveGameObjects();
				repaint();
				checkCrashed();
				checkAlive();
				afterRefresh();
				try {
					Thread.sleep(MILLISEC_PER_FRAME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		private void afterRefresh() {
			frame++;
			notifyRefresh();
		}

		private void notifyRefresh() {
			for (RefreshListener listener : new ArrayList<>(refreshListener)) {
				listener.afterRefresh(BattleForm.this);
			}
		}
	}

	@Override
	public void start() {
		// TODO 确保只能启动一次
		refreshThread.start();
		this.setVisible(true);
	}
	
	@Override
	public void reset() {
		init();
	}

	/**
	 * 让所有能移动的游戏对象移动
	 */
	private void moveGameObjects() {
		for (Movable m : new ArrayList<>(movables)) {
			m.move();
		}
	}

	/**
	 * 检查有生命属性的游戏对象，调用死亡对象的 OnDead 方法并移除对象
	 */
	private void checkAlive() {
		for (Alive alive : new ArrayList<>(alives)) {
			if (!alive.isAlive()) {
				alive.onDead();
				IBCharacter obj = (IBCharacter) alive;
				removeObject(obj);
				for (GameEventListener listener : new ArrayList<>(gameEventListeners)) {
					listener.onObjectRemoved(obj);
				}
			}
		}
	}

	@Override
	protected Image getBufferedImage() {
		Image image = createImage(this.getWidth(), this.getHeight());
		Graphics g = image.getGraphics();
		g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), null);
		PriorityBlockingQueue<Paintable> toPaint = new PriorityBlockingQueue<>(paintables);
		while (!toPaint.isEmpty()) {
			toPaint.poll().paintWith(g);
		}
		return image;
	}

	/**
	 * 将 obj 加入游戏对象列表中，并根据是否实现了以下接口 
	 * <li> {@link Paintable } </li>
	 * <li> {@link Movable } </li>
	 * <li> {@link Alive } </li>
	 *  同时添加到对应队列
	 * 
	 * @param obj
	 */
	@Override
	public void addObject(IBObject obj) {
		this.gameObjs.add(obj);
		if (obj instanceof Paintable)
			addPaintable((Paintable) obj);
		if (obj instanceof Movable)
			addMovable((Movable) obj);
		if (obj instanceof Alive)
			addAlive((Alive) obj);
		if (obj instanceof CanCrash)
			addCrash((CanCrash) obj);
		if (obj instanceof CanCrashed)
			addCanCrashed((CanCrashed) obj);
	}

	/**
	 * 从当前窗体中移除对象
	 * 
	 * @param gameObj
	 */
	@SuppressWarnings("unlikely-arg-type")
	@Override
	public void removeObject(IBObject gameObj) {
		this.gameObjs.remove(gameObj);
		this.paintables.remove(gameObj);
		this.movables.remove(gameObj);
		this.alives.remove(gameObj);
		this.canCrashs.remove(gameObj);
		this.canCrasheds.remove(gameObj);
	}

	private void addCanCrashed(CanCrashed obj) {
		this.canCrasheds.add(obj);
	}

	private void addCrash(CanCrash crashable) {
		this.canCrashs.add(crashable);
	}

	private void addPaintable(Paintable paintable) {
		this.paintables.add(paintable);
	}

	private void addMovable(Movable movable) {
		this.movables.add(movable);
	}

	private void addAlive(Alive alive) {
		this.alives.add(alive);
	}

	public void addKeyboardListener(KeyboardListener listener) {
		this.keyboardListeners.add(listener);
	}

	@Override
	public void addGameEventListener(GameEventListener listener) {
		this.gameEventListeners.add(listener);
	}

	@Override
	public int countOf(Class<? extends IBObject> type) {
		int cnt = 0;
		for (IBObject o : new ArrayList<>(gameObjs)) {
			if (type.isInstance(o))
				cnt++;
		}
		return cnt;
	}

	public void close() {
		setVisible(false);
		dispose();
		closed = true;
	}

	@Override
	public void addRefreshListener(RefreshListener listener) {
		refreshListener.add(listener);
	}

	@Override
	public void removeRefreshListener(RefreshListener listener) {
		refreshListener.remove(listener);
	}

	@Override
	public long currentFrame() {
		return frame;
	}

	@Override
	public int getContentHeight() {
		return getContentPane().getHeight();
	}
}
