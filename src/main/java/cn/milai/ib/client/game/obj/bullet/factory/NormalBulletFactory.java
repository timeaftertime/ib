package cn.milai.ib.client.game.obj.bullet.factory;

import cn.milai.ib.client.game.obj.bullet.Bullet;
import cn.milai.ib.client.game.obj.bullet.NormalBullet;
import cn.milai.ib.client.game.obj.plane.Plane;

public class NormalBulletFactory implements BulletFactory {

	private Plane owner;
	
	public NormalBulletFactory(Plane owner) {
		this.owner = owner;
	}

	@Override
	public Bullet createBullet() {
		return new NormalBullet(owner.getCenterX(), owner.getCenterY(), owner.getContainer(), owner.getCamp(), owner);
	}
	
}
