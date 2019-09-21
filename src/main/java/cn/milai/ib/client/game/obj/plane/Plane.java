package cn.milai.ib.client.game.obj.plane;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.milai.ib.client.game.form.BattleForm;
import cn.milai.ib.client.game.obj.MovableGameEntity;
import cn.milai.ib.client.game.obj.bullet.Bullet;
import cn.milai.ib.client.game.obj.bullet.BulletType;
import cn.milai.ib.client.game.obj.bullet.shooter.BulletShooter;
import cn.milai.ib.client.game.obj.property.CanCrash;
import cn.milai.ib.client.game.obj.property.CanCrashed;
import cn.milai.ib.client.game.obj.property.Explosible;
import cn.milai.ib.client.game.obj.property.HasCamp;
import cn.milai.ib.client.game.obj.property.HasDamage;
import cn.milai.ib.client.game.obj.property.Shootable;

public abstract class Plane extends MovableGameEntity
		implements Shootable, HasCamp, CanCrash, CanCrashed, Explosible, HasDamage {

	private int maxBulletNum = 0;
	private Map<BulletType, BulletShooter> bulletShooters = new ConcurrentHashMap<>();
	private List<Bullet> bullets = new ArrayList<Bullet>();

	private Camp camp;

	public Plane(int x, int y, int width, int height, int speedX, int speedY, int maxBulletNum, int life, Camp camp,
			Image img, BattleForm container) {
		super(x, y, width, height, speedX, speedY, life, img, container);
		this.maxBulletNum = maxBulletNum;
		this.camp = camp;
	}

	public void setBullet(BulletShooter shooter, BulletType type) {
		this.bulletShooters.put(type, shooter);
	}

	public void setBullet(BulletShooter shooter) {
		setBullet(shooter, BulletType.MAIN);
	}

	@Override
	public final void paintWith(Graphics g) {
		if (!isAlive())
			return;
		removeDeadBullet();
		super.paintWith(g);
	}

	private void removeDeadBullet() {
		Iterator<Bullet> ite = bullets.iterator();
		while (ite.hasNext()) {
			Bullet bullet = ite.next();
			if (!bullet.isAlive()) {
				bullet.onDead();
				ite.remove();
			}
		}
	}

	public void shoot() {
		shoot(BulletType.MAIN);
	}

	@Override
	public void shoot(BulletType type) {
		if (!isAlive())
			return;
		if (bullets.size() >= maxBulletNum)
			return;
		if (bulletShooters.get(type) == null)
			return;
		for (Bullet bullet : bulletShooters.get(type).shootBullets()) {
			bullets.add(bullet);
			getContainer().addGameObject(bullet);
		}
	}

	@Override
	public Camp getCamp() {
		return this.camp;
	}

	@Override
	public void damagedBy(HasDamage attacker) {
		if (!isAlive())
			return;
		loseLife(attacker.getDamage());
		if (!isAlive() && !(attacker instanceof Plane))
			attacker.onKill(this);
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

}
