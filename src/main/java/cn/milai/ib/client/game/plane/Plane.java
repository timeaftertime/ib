package cn.milai.ib.client.game.plane;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.milai.ib.client.game.GameObject;
import cn.milai.ib.client.game.bullet.Bullet;
import cn.milai.ib.client.game.bullet.factory.BulletFactory;
import cn.milai.ib.client.game.form.GameForm;
import cn.milai.ib.client.game.property.CanCrash;
import cn.milai.ib.client.game.property.CanCrashed;
import cn.milai.ib.client.game.property.Explosible;
import cn.milai.ib.client.game.property.HasCamp;
import cn.milai.ib.client.game.property.HasDamage;
import cn.milai.ib.client.game.property.Movable;
import cn.milai.ib.client.game.property.Paintable;
import cn.milai.ib.client.game.property.Shootable;

public abstract class Plane extends GameObject
		implements Paintable, Movable, Shootable, HasCamp, CanCrash, CanCrashed, Explosible, HasDamage {

	private int speedX;
	private int speedY;

	private int life;

	private int maxBulletNum = 0;
	private BulletFactory bulletFactory;
	private List<Bullet> bullets = new ArrayList<Bullet>();

	private Camp camp;

	private Image image;

	private GameForm container;

	public Plane(int x, int y, int width, int height, int speedX, int speedY, int maxBulletNum, int life, Camp camp,
			Image image, GameForm container) {
		super(x, y, width, height);
		this.speedX = speedX;
		this.speedY = speedY;
		this.maxBulletNum = maxBulletNum;
		this.life = life;
		this.camp = camp;
		this.image = image;
		this.container = container;
	}

	/**
	 * 返回此该飞机的图像
	 * 
	 * @return
	 */
	public Image getPlaneImage() {
		return this.image;
	}

	@Override
	public GameForm getContainer() {
		return this.container;
	}

	public void setBullet(BulletFactory factory) {
		this.bulletFactory = factory;
	}

	@Override
	public final void paint(Graphics g) {
		if (!isAlive())
			return;
		removeDeadBullet();
		g.drawImage(getPlaneImage(), getX(), getY(), getWidth(), getHeight(), null);
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

	@Override
	public final void move() {
		beforeMove();
		setX(getX() + getSpeedX());
		setY(getY() + getSpeedY());
		afterMove();
	}

	protected abstract void beforeMove();

	protected abstract void afterMove();

	@Override
	public void shoot() {
		if (!isAlive())
			return;
		if (bullets.size() >= maxBulletNum)
			return;
		if (bulletFactory == null)
			return;
		Bullet bullet = bulletFactory.createBullet();
		bullets.add(bullet);
		getContainer().addGameObject(bullet);
	}

	@Override
	public Camp getCamp() {
		return this.camp;
	}

	@Override
	public boolean isAlive() {
		return life > 0;
	}

	@Override
	public void toDead() {
		this.life = 0;
	}

	@Override
	public void damagedBy(HasDamage attacker) {
		if (!isAlive())
			return;
		synchronized (this) {
			life -= attacker.getDamage();
			if (!isAlive())
				attacker.onKill(this);
		}
	}

	public int getMaxBulletNum() {
		return maxBulletNum;
	}

	public void setMaxBulletNum(int maxBulletNum) {
		this.maxBulletNum = maxBulletNum;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}
	
	@Override
	public void onCrash(CanCrashed crashed) {
		crashed.damagedBy(this);
	}

}
