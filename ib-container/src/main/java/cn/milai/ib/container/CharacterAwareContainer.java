package cn.milai.ib.container;

import java.util.List;

import com.google.common.collect.Lists;

import cn.milai.ib.character.explosion.Explosion;
import cn.milai.ib.character.property.CanCrash;
import cn.milai.ib.character.property.Explosible;
import cn.milai.ib.character.property.Movable;
import cn.milai.ib.character.property.Rotatable;
import cn.milai.ib.container.listener.ContainerEventListener;
import cn.milai.ib.contaniner.AbstractLifecycleContainer;
import cn.milai.ib.obj.Camp;
import cn.milai.ib.obj.IBCharacter;
import cn.milai.ib.obj.IBObject;

/**
 * 实现 IBCharacter 属性相关逻辑 Container 抽象基类
 * @author milai
 * @date 2020.03.25
 */
public abstract class CharacterAwareContainer extends AbstractLifecycleContainer {

	private List<IBCharacter> characters;
	private List<Movable> movables;
	private List<CanCrash> canCrashs;

	public CharacterAwareContainer() {
		init();
	}

	@Override
	protected final void resetLifecycleContainer() {
		init();
		resetCharacterAwareContainer();
	}

	private void init() {
		characters = Lists.newArrayList();
		movables = Lists.newArrayList();
		canCrashs = Lists.newArrayList();
		addEventListener(new ContainerEventListener() {
			@Override
			public void onObjectAdded(IBObject obj) {
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
			}
		});
	}

	@Override
	protected final void doRefresh() {
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

	private boolean isCrashed(CanCrash canCrash1, CanCrash canCrash2) {
		if (Camp.sameCamp(canCrash1.getCamp(), canCrash2.getCamp())) {
			return false;
		}
		if (canCrash1 instanceof Rotatable) {
			return canCrash1.intersects(canCrash2);
		}
		return canCrash2.intersects(canCrash1);
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

	/**
	 * 重置容器，在 {@link #resetLifecycleContainer()} 被调用后调用
	 */
	protected abstract void resetCharacterAwareContainer();

	/**
	 * 重新显示容器，在 {@link #doRefresh()} 被调用后调用
	 */
	protected abstract void repaintContainer();

}
