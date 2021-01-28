package cn.milai.ib.character.weapon.bullet.shooter;

import java.util.List;

import com.google.common.collect.Lists;

import cn.milai.ib.character.IBCharacter;
import cn.milai.ib.character.weapon.bullet.Bullet;

/**
 * BulletShooter 抽象实现
 * @author milai
 * @date 2020.04.01
 */
public abstract class AbstractBulletShooter implements BulletShooter {

	/**
	 * 上次发射子弹的帧
	 */
	private long lastShootFrame;

	/**
	 * 最小发射间隔
	 */
	private long shootInterval;

	/**
	 * 最大子弹存活数
	 */
	private int maxBulletNum;

	/**
	 * 已经发射出去的子弹
	 */
	private List<Bullet> bullets = Lists.newArrayList();

	/**
	 * 武器持有者
	 */
	protected IBCharacter owner;

	/**
	 * 创建一个无发射间隔、最大发射子弹数不限的子弹发射器
	 */
	public AbstractBulletShooter(IBCharacter owner) {
		this(owner, 0, Integer.MAX_VALUE);
	}

	public AbstractBulletShooter(IBCharacter owner, long shootInterval, int maxBulletNum) {
		this.owner = owner;
		this.shootInterval = shootInterval;
		this.maxBulletNum = maxBulletNum;
	}

	@Override
	public IBCharacter getOwner() {
		return owner;
	}

	@Override
	public void setOwner(IBCharacter owner) {
		this.owner = owner;
	}

	@Override
	public boolean canShoot() {
		if (lastShootFrame + getShootInterval() >= owner.getContainer().getFrame()) {
			return false;
		}
		for (Bullet bullet : Lists.newArrayList(bullets)) {
			if (!bullet.isAlive()) {
				bullets.remove(bullet);
			}
		}
		if (bullets.size() >= getMaxBulletNum()) {
			return false;
		}
		return true;
	}

	@Override
	public final Bullet[] createBullets() {
		lastShootFrame = owner.getContainer().getFrame();
		Bullet[] newBullets = createBullets0();
		for (Bullet bullet : newBullets) {
			bullets.add(bullet);
		}
		return newBullets;
	}

	/**
	 * 获取发射子弹的最小间隔帧数
	 * @return
	 */
	public long getShootInterval() {
		return shootInterval;
	}

	/**
	 * 设置发射子弹的最小间隔帧数
	 * @param shootInterval
	 */
	public void setShootInterval(long shootInterval) {
		this.shootInterval = shootInterval;
	}

	/**
	 * 获取可以同时存活的子弹数
	 * @return
	 */
	public int getMaxBulletNum() {
		return maxBulletNum;
	}

	/**
	 * 设置可以同时存活的子弹数
	 * @param maxBulletNum
	 */
	public void setMaxBulletNum(int maxBulletNum) {
		this.maxBulletNum = maxBulletNum;
	}

	/**
	 * 实际产生要发射的子弹
	 * @return
	 */
	protected abstract Bullet[] createBullets0();

}
