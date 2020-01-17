package cn.milai.ib.character.plane;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.milai.ib.character.ExplosionCreator;
import cn.milai.ib.character.MovableIBCharacter;
import cn.milai.ib.character.bullet.Bullet;
import cn.milai.ib.character.bullet.shooter.BulletShooter;
import cn.milai.ib.character.explosion.creator.DefaultExplosionCreator;
import cn.milai.ib.constant.BulletType;
import cn.milai.ib.constant.Camp;
import cn.milai.ib.container.Container;
import cn.milai.ib.property.CanCrash;
import cn.milai.ib.property.CanCrashed;
import cn.milai.ib.property.Explosible;
import cn.milai.ib.property.HasCamp;
import cn.milai.ib.property.HasDamage;
import cn.milai.ib.property.Shootable;

/**
 * 战机抽象基类
 *
 * @author milai
 */
public abstract class Plane extends MovableIBCharacter
		implements Shootable, HasCamp, CanCrash, CanCrashed, Explosible, HasDamage {

	private static final String P_MAX_BULLET_NUM = "maxBulletNum";
	private static final String P_SHOOT_INTERVAL = "shootInterval";

	private int maxBulletNum;
	private long shootInterval;
	private long lastShootFrame;

	private Map<BulletType, BulletShooter> bulletShooters = new ConcurrentHashMap<>();
	private List<Bullet> bullets = new ArrayList<Bullet>();

	private Camp camp;

	public Plane(int x, int y, Camp camp, Container container) {
		super(x, y, container);
		this.maxBulletNum = intProp(P_MAX_BULLET_NUM);
		shootInterval = getInitShootInterval();
		lastShootFrame = -shootInterval;
		this.camp = camp;
	}

	public int getInitShootInterval() {
		return intProp(P_SHOOT_INTERVAL);
	}

	protected void setShootInterval(long interval) {
		shootInterval = interval;
	}

	public void setBulletShooter(BulletShooter shooter, BulletType type) {
		this.bulletShooters.put(type, shooter);
	}

	public void setBulletShooter(BulletShooter shooter) {
		setBulletShooter(shooter, BulletType.MAIN);
	}

	public Class<? extends BulletShooter> getBulletShooterClass(BulletType type) {
		return this.bulletShooters.get(type).getClass();
	}

	public Class<? extends BulletShooter> getBulletShooterClass() {
		return getBulletShooterClass(BulletType.MAIN);
	}

	@Override
	public final void paintWith(Graphics g) {
		if (!isAlive()) {
			return;
		}
		removeDeadBullet();
		super.paintWith(g);
	}

	private void removeDeadBullet() {
		Iterator<Bullet> ite = bullets.iterator();
		while (ite.hasNext()) {
			Bullet bullet = ite.next();
			if (!bullet.isAlive()) {
				//				bullet.onDead();
				ite.remove();
			}
		}
	}

	@Override
	public boolean shoot(BulletType type) {
		if (!canShoot(type)) {
			return false;
		}
		for (Bullet bullet : bulletShooters.get(type).shootBullets()) {
			bullets.add(bullet);
			getContainer().addObject(bullet);
		}
		lastShootFrame = getContainer().currentFrame();
		return true;
	}

	protected boolean canShoot(BulletType type) {
		return isAlive() && (bulletShooters.get(type) != null) && (bullets.size() < maxBulletNum)
				&& (getContainer().currentFrame() >= lastShootFrame + shootInterval);
	}

	@Override
	public Camp getCamp() {
		return this.camp;
	}

	@Override
	public void damagedBy(HasDamage attacker) {
		if (!isAlive()) {
			return;
		}
		loseLife(attacker.getDamage());
		// 只有通过子弹消灭的敌机才能得分
		// 目前 onKill 只用于玩家得分，所以暂时可以用这种方式实现
		if (!isAlive() && (attacker instanceof Bullet)) {
			attacker.onKill(this);
		}
	}

	public int getMaxBulletNum() {
		return maxBulletNum;
	}

	public void setMaxBulletNum(int maxBulletNum) {
		this.maxBulletNum = maxBulletNum;
	}

	@Override
	public void onCrash(CanCrashed crashed) {
		crashed.damagedBy(this);
	}

	@Override
	public Class<? extends ExplosionCreator> getExplosionCreator() {
		return DefaultExplosionCreator.class;
	}

}
