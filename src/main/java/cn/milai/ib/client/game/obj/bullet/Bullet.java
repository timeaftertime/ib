package cn.milai.ib.client.game.obj.bullet;

import java.awt.Graphics;
import java.awt.Image;

import cn.milai.ib.client.game.form.GameForm;
import cn.milai.ib.client.game.obj.GameEntity;
import cn.milai.ib.client.game.obj.plane.Plane;
import cn.milai.ib.client.game.obj.property.Alive;
import cn.milai.ib.client.game.obj.property.CanCrash;
import cn.milai.ib.client.game.obj.property.HasDamage;
import cn.milai.ib.client.game.obj.property.Movable;
import cn.milai.ib.client.game.obj.property.Paintable;

public abstract class Bullet extends GameEntity implements Paintable, Movable, CanCrash, HasDamage {
	
	private Image img;
	
	private Camp camp;
	
	private Plane owner;
	
	protected Bullet(int centerX, int centerY, int width, int height, int power, Image img, Camp camp, Plane owner, GameForm container) {
		super(centerX - width / 2, centerY, width, height, container);
		this.img = img;
		this.camp = camp;
		this.owner = owner;
	}
	
	@Override
	public void paintWith(Graphics g) {
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
