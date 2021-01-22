package cn.milai.ib.container;

import java.util.List;

import com.google.common.collect.Lists;

import cn.milai.ib.IBObject;
import cn.milai.ib.character.Camp;
import cn.milai.ib.character.IBCharacter;
import cn.milai.ib.character.explosion.Explosion;
import cn.milai.ib.character.property.CanCrash;
import cn.milai.ib.character.property.Explosible;
import cn.milai.ib.character.property.Movable;
import cn.milai.ib.character.property.Rotatable;
import cn.milai.ib.container.control.Command;
import cn.milai.ib.container.control.CommandDispatcher;
import cn.milai.ib.container.control.Controllable;
import cn.milai.ib.container.control.ControllableContainer;
import cn.milai.ib.container.lifecycle.AbstractLifecycleContainer;
import cn.milai.ib.container.ui.UIContainer;
import cn.milai.ib.geometry.Rect;

/**
 * 实现 {@link IBCharacter} 属性相关逻辑 {@link Container} 抽象基类
 * @author milai
 * @date 2020.03.25
 */
public abstract class CharacterAwareContainer extends AbstractLifecycleContainer
	implements UIContainer, ControllableContainer {

	/**
	 * 每一帧每个 fromId 能被响应的最大指令数
	 */
	private static final int MAX_COMMAND_PER_FRAME = 5;

	private int width;
	private int height;

	private List<IBCharacter> characters;
	private List<Movable> movables;
	private List<CanCrash> canCrashs;

	private CommandDispatcher commandDispatcher;

	public CharacterAwareContainer() {
		init();
	}

	@Override
	protected final void resetLifecycleContainer() {
		init();
		resetCharacterAwareContainer();
	}

	private void init() {
		characters = Lists.newCopyOnWriteArrayList();
		movables = Lists.newCopyOnWriteArrayList();
		canCrashs = Lists.newCopyOnWriteArrayList();
		commandDispatcher = new CommandDispatcher();
		addEventListener(new ContainerEventListener() {
			@Override
			public void onObjectAdded(IBObject obj) {
				if (obj instanceof Controllable) {
					commandDispatcher.addControllable((Controllable) obj);
				}
				if (obj instanceof IBCharacter) {
					characters.add((IBCharacter) obj);
					if (obj instanceof Movable) {
						movables.add((Movable) obj);
					}
					if (obj instanceof CanCrash) {
						canCrashs.add((CanCrash) obj);
					}
				}
			}

			@Override
			public void onObjectRemoved(IBObject obj) {
				characters.remove(obj);
				movables.remove(obj);
				canCrashs.remove(obj);
				if (obj instanceof Controllable) {
					commandDispatcher.removeControllable((Controllable) obj);
				}
			}
		});
	}

	@Override
	protected final void doRefresh() {
		reponseCommands();
		if (!isPined()) {
			moveCharacters();
			checkCrash();
			checkAlive();
		}
		repaintContainer();
	}

	private void checkAlive() {
		for (IBCharacter character : Lists.newArrayList(characters)) {
			if (!character.isAlive()) {
				character.onDead();
				showExplosions(character);
				removeObject(character);
			}
		}

	}

	private void checkCrash() {
		List<CanCrash> canCrashs = Lists.newArrayList(this.canCrashs);
		for (int i = 1; i < canCrashs.size(); i++) {
			for (int j = 0; j < i; j++) {
				CanCrash canCrash1 = canCrashs.get(i);
				CanCrash canCrash2 = canCrashs.get(j);
				if (isCrashed(canCrash1, canCrash2)) {
					canCrash1.onCrash(canCrash2);
					canCrash2.onCrash(canCrash1);
				}
			}
		}
	}

	private void moveCharacters() {
		for (Movable m : Lists.newArrayList(movables)) {
			m.move();
		}
	}

	/**
	 * 响应指令
	 */
	private void reponseCommands() {
		for (int i = 0; i < MAX_COMMAND_PER_FRAME; i++) {
			commandDispatcher.dispathOne();
		}
	}

	/**
	 * 判断两个角色是否发生碰撞
	 * @param c1
	 * @param c2
	 * @return
	 */
	public static boolean isCrashed(IBCharacter c1, IBCharacter c2) {
		if (Camp.sameCamp(c1.getCamp(), c2.getCamp())) {
			return false;
		}
		if ((c1 instanceof Rotatable) || (c2 instanceof Rotatable)) {
			return new Rect(c1.getRealBoundPoints()).intersects(new Rect(c2.getRealBoundPoints()));
		}
		return c1.getX() + c1.getWidth() >= c2.getX()
			&& c1.getX() <= c2.getX() + c2.getWidth()
			&& c1.getY() + c1.getHeight() >= c2.getY()
			&& c1.getY() <= c2.getY() + c2.getHeight();
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
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void addCommand(Command c) {
		if (isPaused()) {
			return;
		}
		commandDispatcher.addCommand(c);
	}

	/**
	 * 重置容器，在 {@link #resetLifecycleContainer()} 被调用后调用
	 */
	protected abstract void resetCharacterAwareContainer();

	/**
	 * 重新显示容器，在 {@link #doRefresh()} 被调用后调用
	 */
	protected abstract void repaintContainer();

}
