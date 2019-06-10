package cn.milai.ib.client.game.bullet;

import java.awt.Graphics;
import java.awt.Image;

import cn.milai.ib.client.game.GameObject;
import cn.milai.ib.client.game.plane.Plane;
import cn.milai.ib.client.game.property.Alive;
import cn.milai.ib.client.game.property.CanCrash;
import cn.milai.ib.client.game.property.HasDamage;
import cn.milai.ib.client.game.property.Movable;
import cn.milai.ib.client.game.property.Paintable;

public abstract class Bullet extends GameObject implements Paintable, Movable, CanCrash, HasDamage {
	
	private Image img;
	
	private Camp camp;
	
	private Plane owner;
	
	protected Bullet(int centerX, int centerY, int width, int height, int power, Image img, Camp camp, Plane owner) {
		super(centerX - width / 2, centerY, width, height);
		this.img = img;
		this.camp = camp;
		this.owner = owner;
	}
	
	@Override
	public void paint(Graphics g) {
		if(!isAlive())
			return;
		g.drawImage(img, getX(), getY(), getWidth(), getHeight(), null);
	}

	@Override
	public final void move() {
		if(!isAlive())
			return;
		simpleMove();
		if(outOfContainer())
			this.toDead();
	}
	
	public abstract void simpleMove();
	
	private boolean outOfContainer() {
		if(this.getX() > getContainer().getWidth())
			return true;
		if(this.getY() > getContainer().getHeight())
			return true;
		if(this.getX() + getWidth() < 0)
			return true;
		if(this.getY() + this.getHeight() < 0)
			return true;
		return false;
	}
	
	@Override
	public Camp getCamp() {
		return this.camp;
	}
	
	@Override
	public void onKill(Alive alive) {
		owner.onKill(alive);
	}
	
	@Override
	public int getScore() {
		return 0;
	}
}
