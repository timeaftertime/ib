package cn.milai.ib.character.property;

import java.awt.Point;

import cn.milai.ib.obj.IBObject;

/**
 * 移动时会根据速度方向而改变朝向的角色
 * @author milai
 * @date 2020.03.10
 */
public interface Rotatable extends Movable {

	/**
	 * 获取游戏对象的实际边界点坐标
	 * @param obj
	 * @return 一个长度为 4 的 Point 数组，分别表示角色左上、左下、右下、右上点的坐标
	 */
	static Point[] getRealBoundPoints(IBObject obj) {
		int x = obj.getX();
		int y = obj.getY();
		int width = obj.getWidth();
		int height = obj.getHeight();
		Point[] points = new Point[] {
			new Point(x, y),
			new Point(x, y + height),
			new Point(x + width, y + height),
			new Point(x + width, y),
		};
		if (obj instanceof Rotatable) {
			Rotatable rotatable = (Rotatable) obj;
			for (int i = 0; i < points.length; i++) {
				points[i] = rotatable.rotate(points[i]);
			}
		}
		return points;
	}

	/**
	 * 速度方向与 Y 轴正方向的夹角的正弦值
	 * @return
	 */
	default double sinSpeed() {
		return Math.sin(speedRadian());
	}

	/**
	 * 速度方向与 Y 轴正方向的夹角的余弦值
	 * @return
	 */
	default double cosSpeed() {
		return Math.cos(speedRadian());
	}

	/**
	 * 需要旋转的角度，即速度方向与 Y 轴正方向的夹角的弧度值
	 * @return
	 */
	default double speedRadian() {
		if (getSpeedX() == 0 && getSpeedY() == 0) {
			return 0;
		}
		if (getSpeedX() == 0) {
			if (getSpeedY() > 0) {
				return 0;
			}
			return Math.PI;
		}
		if (getSpeedY() == 0) {
			if (getSpeedX() > 0) {
				return 0.5 * Math.PI;
			}
			return -0.5 * Math.PI;
		}
		if (getSpeedY() < 0) {
			return Math.PI + Math.atan(1.0 * getSpeedX() / getSpeedY());
		}
		return Math.atan(1.0 * getSpeedX() / getSpeedY());
	}

	/**
	 * 获取当前角色的某个 point 在旋转后的对应 Point
	 * @param point
	 * @return
	 */
	default Point rotate(Point point) {
		double x = getCenterX();
		double y = getCenterY();
		int x1 = point.x;
		int y1 = point.y;
		double x2 = x + (x1 - x) * cosSpeed() + (y1 - y) * sinSpeed();
		double y2 = y + (y1 - y) * cosSpeed() + (x - x1) * sinSpeed();
		// 四舍五入
		x2 = x2 > 0 ? x2 + 0.5 : x2 - 0.5;
		y2 = y2 > 0 ? y2 + 0.5 : y2 - 0.5;
		return new Point((int) x2, (int) y2);
	}
}
