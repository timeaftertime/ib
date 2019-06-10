package cn.milai.ib.client.game.form;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import cn.milai.ib.client.game.GameObject;
import cn.milai.ib.client.game.conf.BattleConf;
import cn.milai.ib.client.game.conf.FormSizeConf;
import cn.milai.ib.client.game.conf.ImageConf;
import cn.milai.ib.client.game.property.Alive;
import cn.milai.ib.client.game.property.CanCrash;
import cn.milai.ib.client.game.property.CanCrashed;
import cn.milai.ib.client.game.property.Movable;
import cn.milai.ib.client.game.property.Paintable;

public class BattleForm extends DoubleBufferForm {

	private static final long serialVersionUID = 1L;

	private Image bgImage = ImageConf.BATTLE_BG;
	private List<GameObject> gameObjs = new ArrayList<GameObject>();
	private List<Paintable> paintables = new ArrayList<Paintable>();
	private List<Movable> movables = new ArrayList<Movable>();
	private List<Alive> alives = new ArrayList<Alive>();
	private List<CanCrash> canCrashs = new ArrayList<CanCrash>();
	private List<CanCrashed> canCrasheds = new ArrayList<CanCrashed>();
	private List<KeyboardListener> keyboardListeners = new ArrayList<KeyboardListener>();
	private List<GameEventListener> gameEventListeners = new ArrayList<GameEventListener>();
	
	private Thread refreshThread;
	
	private boolean gameOver = false;
	private boolean closed = false;
	
	public void setGameOver() {
		gameOver =true;
	}
	
	public boolean isGameOver() {
		return gameOver;
	}
	
	public BattleForm() {
		init();
	}
	
	private void init() {
		this.setTitle(BattleConf.FORM_TITLE);
		this.setSize(FormSizeConf.BATTLE_WIDTH, FormSizeConf.BATTLE_HEIGHT);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		
		// 窗口关闭监听
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
				for(GameEventListener listener :new ArrayList<>( gameEventListeners)) {
					listener.onFormClosed();
				}
				closed = true;
			}
		});
		
		// 用于刷新的线程
		refreshThread = new Thread(()-> {
			while(!closed) {
				moveGameObjects();
				repaint();
				checkAlive();
				checkCrashed();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		// 按键监听器
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				Command action = BattleConf.keySettings.get(e.getKeyCode());
				if(action == null)
					return;
				for(KeyboardListener listener : keyboardListeners)
					listener.keyDown(action);
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				Command action = BattleConf.keySettings.get(e.getKeyCode());
				if(action == null)
					return;
				for(KeyboardListener listener : keyboardListeners)
					listener.keyUp(action);
			}
		});
	}
	
	private void checkCrashed() {
		for(CanCrash crash : new ArrayList<>(canCrashs)) {
			for(CanCrashed crashed : new ArrayList<>(canCrasheds)) {
				if(crash.sameCamp(crashed))
					continue;
				if(((GameObject) crash).isContactWith((GameObject) crashed)) {
					crash.onCrash(crashed);
				}
			}
		}
	}

	public void start() {
		refreshThread.start();
		this.setVisible(true);
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
	 * 检查有生命属性的游戏对象，调用死亡对象的 Ondead 方法并移除对象
	 */
	private void checkAlive() {
		for(Alive alive : new ArrayList<>(alives)) {
			if(!alive.isAlive()) {
				alive.onDead();
				GameObject obj = (GameObject) alive;
				removeGameObject(obj);
				for(GameEventListener listener : new ArrayList<>(gameEventListeners)) {
					listener.onGameObjectDead(obj);
				}
			}
		}
	}
	
	/**
	 * 从 GameObject、Paintable、Movable 列表移除对象
	 * @param gameObj
	 */
	@SuppressWarnings("unlikely-arg-type")
	public void removeGameObject(GameObject gameObj) {
		this.gameObjs.remove(gameObj);
		this.paintables.remove(gameObj);
		this.movables.remove(gameObj);
		this.alives.remove(gameObj);
		this.canCrashs.remove(gameObj);
		this.canCrasheds.remove(gameObj);
	}
	
	@Override
	protected Image getBufferedImage() {
		Image image = createImage(this.getWidth(), this.getHeight());
		Graphics g = image.getGraphics();
		g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), null);
		for(Paintable p : new ArrayList<>(paintables)) {
			p.paint(g);
		}
		return image;
	}
	
	/**
	 * 将 obj 加入游戏对象列表中，并根据是否实现了以下接口
	 * <li>
	 *  	{@link Paintable }
	 *  	{@link Movable }
	 *  	{@link Alive }
	 *  </li>
	 *  同时添加到对应队列
	 * @param obj
	 */
	public void addGameObject(GameObject obj) {
		this.gameObjs.add(obj);
		if(obj instanceof Paintable)
			addPaintable((Paintable) obj);
		if(obj instanceof Movable)
			addMovable((Movable) obj);
		if(obj instanceof Alive)
			addAlive((Alive) obj);
		if(obj instanceof CanCrash)
			addCrash((CanCrash) obj);
		if(obj instanceof CanCrashed)
			addCanCrashed((CanCrashed) obj);
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
	
	public void addGameEventListener(GameEventListener listener) {
		this.gameEventListeners.add(listener);
	}

	public int getGameObjectCnt(Class<?> type) {
		int cnt = 0;
		for(GameObject o : new ArrayList<>(gameObjs)) {
			if(type.isInstance(o))
				cnt++;
		}
		return cnt;
	}
}
