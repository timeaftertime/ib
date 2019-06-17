package cn.milai.ib.client.game.obj.bullet.factory;

import cn.milai.ib.client.game.obj.bullet.Bullet;
import cn.milai.ib.client.game.obj.bullet.EnemyBullet;
import cn.milai.ib.client.game.obj.plane.Plane;

public class EnemyBulletFactory implements BulletFactory {

	public Plane owner;
	
	public EnemyBulletFactory(Plane owner) {
		this.owner = owner;
	}
	
	@Override
	public Bullet createBullet() {
		return new EnemyBullet(owner.getCenterX(), owner.getCenterY(), owner.getContainer(), owner.getCamp(), owner);
	}

}
