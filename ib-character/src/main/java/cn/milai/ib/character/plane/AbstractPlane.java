package cn.milai.ib.character.plane;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.milai.ib.character.MovableIBCharacter;
import cn.milai.ib.character.WeaponType;
import cn.milai.ib.character.bullet.Bullet;
import cn.milai.ib.character.bullet.shooter.BulletShooter;
import cn.milai.ib.character.explosion.creator.DefaultExplosionCreator;
import cn.milai.ib.character.explosion.creator.ExplosionCreator;
import cn.milai.ib.character.property.CanCrash;
import cn.milai.ib.character.property.HasDamage;
import cn.milai.ib.container.Container;
import cn.milai.ib.obj.Camp;

/**
 * 战机抽象基类
 * @author milai
 */
public abstract class AbstractPlane extends MovableIBCharacter implements Plane, HasDamage {

	private int maxBulletNum;
	private long shootInterval;
	private long lastShootFrame;

	private ExplosionCreator explosionCreator;

	private Map<WeaponType, BulletShooter> bulletShooters = new ConcurrentHashMap<>();
	private List<Bullet> bullets = new ArrayList<>();

	private Camp camp;

	public AbstractPlane(int x, int y, Camp camp, Container container) {
		super(x, y, container);
		this.maxBulletNum = intProp(P_MAX_BULLET_NUM);
		shootInterval = getInitShootInterval();
		lastShootFrame = -shootInterval;
		this.camp = camp;
		explosionCreator = new DefaultExplosionCreator();
	}

	@Override
	public int getInitShootInterval() {
		return intProp(P_SHOOT_INTERVAL);
	}

	protected void setShootInterval(long interval) {
		shootInterval = interval;
	}

	@Override
	public void setBulletShooter(BulletShooter shooter, WeaponType type) {
		this.bulletShooters.put(type, shooter);
	}

	@Override
	public BulletShooter getBulletShooter(WeaponType type) {
		return this.bulletShooters.get(type);
	}

	@Override
	public BulletShooter getBulletShooter() {
		return getBulletShooter(WeaponType.MAIN);
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
				ite.remove();
			}
		}
	}

	@Override
	public boolean shoot(WeaponType type) {
		if (!canShoot(type)) {
			return false;
		}
		for (Bullet bullet : bulletShooters.get(type).shootBullets(this)) {
			bullets.add(bullet);
			getContainer().addObject(bullet);
		}
		lastShootFrame = getContainer().currentFrame();
		return true;
	}

	protected boolean canShoot(WeaponType type) {
		return isAlive()
			&& (bulletShooters.get(type) != null)
			&& (bullets.size() < maxBulletNum)
			&& (getContainer().currentFrame() >= lastShootFrame + shootInterval);
	}

	@Override
	public Camp getCamp() {
		return this.camp;
	}

	@Override
	public ExplosionCreator getExplosionCreator() {
		return explosionCreator;
	}

	@Override
	public int getMaxBulletNum() {
		return maxBulletNum;
	}

	@Override
	public void setMaxBulletNum(int maxBulletNum) {
		this.maxBulletNum = maxBulletNum;
	}

	@Override
	public void onCrash(CanCrash crashed) {
		crashed.loseLife(this, getDamage());
	}

}
