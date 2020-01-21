package cn.milai.ib.form;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import cn.milai.ib.IBObject;
import cn.milai.ib.character.IBCharacter;
import cn.milai.ib.conf.ImageConf;
import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.container.listener.GameEventListener;
import cn.milai.ib.container.listener.RefreshListener;
import cn.milai.ib.interaction.form.FormContainer;
import cn.milai.ib.interaction.form.listener.Command;
import cn.milai.ib.interaction.form.listener.FormCloseListener;
import cn.milai.ib.interaction.form.listener.KeyboardListener;
import cn.milai.ib.property.Alive;
import cn.milai.ib.property.CanCrash;
import cn.milai.ib.property.CanCrashed;
import cn.milai.ib.property.Movable;
import cn.milai.ib.property.Paintable;
import cn.milai.ib.util.OrderUtil;
import cn.milai.ib.util.TimeUtil;

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
	private List<RefreshListener> refreshListeners;
	private List<FormCloseListener> formCloseListeners;

	private Thread refreshThread;

	/**
	 * 窗口是否被关闭
	 */
	private volatile boolean closed;

	/**
	 * 是否被暂停
	 */
	private volatile boolean paused;

	/**
	 * 游戏对象是否被固定住
	 */
	private volatile boolean pin;

	/**
	 * 是否调用过 start 方法
	 */
	private boolean started;

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
		gameObjs = Lists.newArrayList();
		paintables = Lists.newArrayList();
		movables = Lists.newArrayList();
		alives = Lists.newArrayList();
		canCrashs = Lists.newArrayList();
		canCrasheds = Lists.newArrayList();
		keyboardListeners = Lists.newArrayList();
		gameEventListeners = Lists.newArrayList();
		refreshListeners = Lists.newArrayList();
		formCloseListeners = Lists.newArrayList();
		closed = false;
		paused = false;
		started = false;
		pin = false;

		// 窗口关闭监听
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
				for (FormCloseListener listener : new ArrayList<>(formCloseListeners)) {
					listener.onFormClosed();
				}
				close();
			}
		});

		// 用于刷新的线程
		refreshThread = new RefreshThread();

		// 按键监听器
		this.addKeyListener(new KeyEventDispatcher());
	}

	private void checkCrash() {
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

	/**
	 * 界面刷新和游戏进度线程
	 * 2020.01.12
	 * @author milai
	 */
	private class RefreshThread extends Thread {

		private static final String THREAD_NAME_PREFIX = "Refresh#started at ";

		public RefreshThread() {
			setName(THREAD_NAME_PREFIX + TimeUtil.datetime());
		}

		@Override
		public void run() {
			while (!closed) {
				checkPaused();

				if (!pin) {
					moveCharacters();
					checkCrash();
					checkAlive();
					frame++;
					notifyRefresh();
				}
				repaint();

				try {
					Thread.sleep(MILLISEC_PER_FRAME);
				} catch (InterruptedException e) {}
			}
		}

		private void checkPaused() {
			if (paused) {
				try {
					while (!Thread.interrupted()) {
						Thread.sleep(MILLISEC_PER_FRAME);
					}
				} catch (InterruptedException e) {
					// 依然走 finally 逻辑
				} finally {
					paused = false;
				}
			}
		}

		private void notifyRefresh() {
			for (RefreshListener listener : new ArrayList<>(refreshListeners)) {
				listener.afterRefresh(BattleForm.this);
			}
		}
	}

	/**
	 * 键盘事件分发器
	 * 2020.01.12
	 * @author milai
	 */
	private class KeyEventDispatcher implements KeyboardListener {

		@Override
		public boolean keyDown(Command e) {
			for (KeyboardListener listener : keyboardListeners) {
				if (listener.keyDown(e)) {
					return true;
				}
			}
			return false;
		}

		@Override
		public boolean keyUp(Command e) {
			for (KeyboardListener listener : keyboardListeners) {
				if (listener.keyUp(e)) {
					return true;
				}
			}
			return false;
		}

	}

	@Override
	public void start() {
		if (started) {
			return;
		}
		synchronized (this) {
			if (started) {
				return;
			}
			started = true;
		}
		this.setVisible(true);
		refreshThread.start();
	}

	@Override
	public void reset() {
		init();
	}

	/**
	 * 让所有能移动的游戏对象移动
	 */
	private void moveCharacters() {
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
		ArrayList<Paintable> toPaint = Lists.newArrayList(paintables);
		Collections.sort(toPaint);
		for (Paintable p : toPaint) {
			p.paintWith(g);
		}
		return image;
	}

	/**
	 * 将游戏对象加入游戏对象列表中，并根据是否实现了对应接口，同时添加到对应队列
	 * @see Paintable
	 * @see Movable
	 * @see Alive
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
		for (GameEventListener listener : gameEventListeners) {
			listener.onObjectAdded(obj);
		}
	}

	/**
	 * 从当前窗体中移除对象
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

	@Override
	public void addKeyboardListener(KeyboardListener listener) {
		this.keyboardListeners.add(listener);
		OrderUtil.sort(this.keyboardListeners);
	}

	@Override
	public void removeKeyboardListener(KeyboardListener listener) {
		this.keyboardListeners.remove(listener);
	}

	@Override
	public void addGameEventListener(GameEventListener listener) {
		this.gameEventListeners.add(listener);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getAll(Class<T> type) {
		List<T> objs = Lists.newArrayList();
		for (IBObject o : new ArrayList<>(gameObjs)) {
			if (type.isInstance(o))
				objs.add((T) o);
		}
		return objs;
	}

	public void close() {
		setVisible(false);
		dispose();
		closed = true;
		refreshThread.interrupt();
	}

	@Override
	public void addRefreshListener(RefreshListener listener) {
		refreshListeners.add(listener);
	}

	@Override
	public void removeRefreshListener(RefreshListener listener) {
		refreshListeners.remove(listener);
	}

	@Override
	public long currentFrame() {
		return frame;
	}

	@Override
	public int getContentHeight() {
		return getContentPane().getHeight();
	}

	@Override
	public void pauseOrResume() {
		if (paused) {
			refreshThread.interrupt();
			// paused 的重置由 refreshThread 实现，以确保其退出 pause 状态才重置 paused
		} else {
			paused = true;
		}
	}

	@Override
	public void addFormCloseListener(FormCloseListener listener) {
		formCloseListeners.add(listener);
	}

	@Override
	public void setPin(boolean pin) {
		this.pin = pin;
	}

}
