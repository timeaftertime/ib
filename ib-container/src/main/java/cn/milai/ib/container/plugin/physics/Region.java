package cn.milai.ib.container.plugin.physics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.milai.ib.geometry.BaseBounds;
import cn.milai.ib.geometry.Rect;
import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.Rotatable;

/**
 * 区域
 * @author milai
 * @date 2021.04.06
 */
public class Region {

	private Rect rect;
	private Set<Role> roles = new HashSet<>();

	/**
	 * 以指定点、宽度、高度构造一个 {@link Region}
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public Region(double x, double y, double w, double h) {
		rect = new Rect(new BaseBounds(x, y, w, h).toPoints());
	}

	/**
	 * 判断指定 {@link Role} 是否在当前区域内
	 * @param role
	 * @return
	 */
	private boolean intersects(Role role) {
		return rect.intersects(Rotatable.getBoundRect(role));
	}

	/**
	 * 若指定 {@link Role} 在当前区域，添加到当前区域，否则从当前区域移除该 {@link Role}
	 * @param role
	 * @return
	 */
	public void check(Role role) {
		if (intersects(role)) {
			roles.add(role);
		} else {
			roles.remove(role);
		}
	}

	/**
	 * 获取当前区域中所有 {@link Role}
	 * @return
	 */
	public List<Role> getRoles() { return new ArrayList<>(roles); }

	/**
	 * 判断当前 {@link Region} 的 {@link Role} 列表是否包含指定 {@link Role}
	 * @param role
	 * @return
	 */
	public boolean has(Role role) {
		return roles.contains(role);
	}

}
