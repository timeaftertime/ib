package cn.milai.ib.form;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.milai.ib.character.explosion.Explosion;
import cn.milai.ib.character.property.CanCrash;
import cn.milai.ib.character.property.CanCrashed;
import cn.milai.ib.character.property.Explosible;
import cn.milai.ib.character.property.Movable;
import cn.milai.ib.component.form.FormContainer;
import cn.milai.ib.component.form.listener.Command;
import cn.milai.ib.component.form.listener.FormCloseListener;
import cn.milai.ib.component.form.listener.KeyboardListener;
import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.constant.Camp;
import cn.milai.ib.container.Audio;
import cn.milai.ib.container.listener.GameEventListener;
import cn.milai.ib.container.listener.RefreshListener;
import cn.milai.ib.obj.IBCharacter;
import cn.milai.ib.obj.IBObject;
import cn.milai.ib.obj.Paintable;
import cn.milai.ib.util.OrderUtil;
import cn.milai.ib.util.TimeUtil;

/**
 * 战斗场景窗体类
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
	private List<IBObject> objs;
	private List<IBCharacter> characters;
	private List<Movable> movables;
	private List<CanCrash> canCrashs;
	private List<CanCrashed> canCrasheds;
	private List<KeyboardListener> keyboardListeners;
	private List<GameEventListener> gameEventListeners;
	private List<RefreshListener> refreshListeners;
	private List<FormCloseListener> formCloseListeners;

	private Map<String, Audio> audios;

	private Thread refreshThread;
	private Thread audioThread;

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

		objs = Lists.newArrayList();
		characters = Lists.newArrayList();
		movables = Lists.newArrayList();
		canCrashs = Lists.newArrayList();
		canCrasheds = Lists.newArrayList();
		keyboardListeners = Lists.newArrayList();
		gameEventListeners = Lists.newArrayList();
		refreshListeners = Lists.newArrayList();
		formCloseListeners = Lists.newArrayList();
		audios = Maps.newConcurrentMap();
		closed = false;
		paused = false;
		started = false;
		pin = false;

		// 窗口关闭监听
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
				for (FormCloseListener listener : Lists.newArrayList(formCloseListeners)) {
					listener.onFormClosed();
				}
				close();
			}
		});

		refreshThread = new RefreshThread();
		audioThread = new AudioPlayThread();

		// 按键监听器
		this.addKeyListener(new KeyEventDispatcher());
	}

	private void checkCrash() {
		for (CanCrash crash : Lists.newArrayList(canCrashs)) {
			for (CanCrashed crashed : Lists.newArrayList(canCrasheds)) {
				if (crash == crashed) {
					continue;
				}
				if (Camp.sameCamp(crash.getCamp(), crashed.getCamp())) {
					continue;
				}
				if (crash.intersects(crashed)) {
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

		private static final String THREAD_NAME_PREFIX = "Refresh  ";

		public RefreshThread() {
			setName(THREAD_NAME_PREFIX + TimeUtil.datetime());
		}

		@Override
		public void run() {
			while (true) {
				if (closed) {
					frame = -1;
					notifyRefresh();
					break;
				}
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
				} catch (InterruptedException e) {
				}
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
			for (RefreshListener listener : Lists.newArrayList(refreshListeners)) {
				listener.afterRefresh(BattleForm.this);
			}
		}
	}

	/**
	 * 音频线程
	 * 2020.01.25
	 * @author milai
	 */
	private class AudioPlayThread extends Thread {
		private static final int THREAD_POOL_SIZE = 2;
		private static final String THREAD_NAME_PREFIX = "AudioPlayThread#started at ";
		// 已经被某个线程服务的音频
		private List<String> fetched = Lists.newArrayList();
		private ExecutorService pool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);;

		public AudioPlayThread() {
			setName(THREAD_NAME_PREFIX + TimeUtil.datetime());
		}

		@Override
		public void run() {
			while (true) {
				if (closed) {
					pool.shutdown();
					return;
				}
				for (String code : audios.keySet()) {
					Audio audio = audios.get(code);
					// 判断 contains 和 add 操作都在 Audio 主线程，不需要同步
					if (!fetched.contains(audio.getCode())) {
						fetched.add(audio.getCode());
						pool.execute(() -> {
							audio.play();
							if (audio.isComplete()) {
								audios.remove(code, audio);
							}
							fetched.remove(audio.getCode());
						});
					}
				}
				TimeUtil.wait(BattleForm.this, 1L);
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
		audioThread.start();
	}

	@Override
	public void reset() {
		init();
	}

	/**
	 * 让所有能移动的游戏对象移动
	 */
	private void moveCharacters() {
		for (Movable m : Lists.newArrayList(movables)) {
			m.move();
		}
	}

	/**
	 * 处理有生命属性的游戏对象
	 */
	private void checkAlive() {
		for (IBCharacter character : Lists.newArrayList(characters)) {
			if (!character.isAlive()) {
				character.onDead();
				showExplosions(character);
				removeObject(character);
				for (GameEventListener listener : Lists.newArrayList(gameEventListeners)) {
					listener.onObjectRemoved(character);
				}
			}
		}
	}

	private void showExplosions(IBObject obj) {
		if (!(obj instanceof Explosible)) {
			return;
		}
		Explosible explosible = (Explosible) obj;
		for (Explosion explosion : explosible.getExplosionCreator().createExplosions(explosible)) {
			addObject(explosion);
		}
	}

	@Override
	protected Image getBufferedImage() {
		Image image = createImage(this.getWidth(), this.getHeight());
		Graphics g = image.getGraphics();
		if (bgImage != null) {
			g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), null);
		}
		List<Paintable> toPaint = Lists.newArrayList(objs);
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
		this.objs.add(obj);
		if (obj instanceof IBCharacter) {
			addCharacter((IBCharacter) obj);
		}
		for (GameEventListener listener : gameEventListeners) {
			listener.onObjectAdded(obj);
		}
	}

	/**
	 * 从当前窗体中移除对象
	 * @param ibObj
	 */
	@Override
	public void removeObject(IBObject ibObj) {
		this.objs.remove(ibObj);
		this.movables.remove(ibObj);
		this.characters.remove(ibObj);
		this.canCrashs.remove(ibObj);
		this.canCrasheds.remove(ibObj);
	}

	private void addCharacter(IBCharacter character) {
		this.characters.add(character);
		if (character instanceof Movable)
			addMovable((Movable) character);
		if (character instanceof CanCrash)
			addCrash((CanCrash) character);
		if (character instanceof CanCrashed)
			addCanCrashed((CanCrashed) character);
	}

	private void addCanCrashed(CanCrashed canChrashed) {
		this.canCrasheds.add(canChrashed);
	}

	private void addCrash(CanCrash crashable) {
		this.canCrashs.add(crashable);
	}

	private void addMovable(Movable movable) {
		this.movables.add(movable);
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
		List<T> allOfType = Lists.newArrayList();
		for (IBObject o : Lists.newArrayList(objs)) {
			if (type.isInstance(o))
				allOfType.add((T) o);
		}
		return allOfType;
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

	@Override
	public void playAudio(Audio audio) {
		if (audio == null) {
			return;
		}
		audios.put(audio.getCode(), audio);
	}

	@Override
	public void stopAudio(String code) {
		audios.remove(code);
	}

	@Override
	public void setBackgroud(Image img) {
		this.bgImage = img;
	}

}
