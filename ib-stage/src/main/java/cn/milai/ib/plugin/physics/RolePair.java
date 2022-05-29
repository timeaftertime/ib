package cn.milai.ib.plugin.physics;

import cn.milai.ib.role.Role;

/**
 * 两个 {@link Role} 的组合 key
 * @author milai
 * @date 2021.04.16
 */
public class RolePair {

	private Role r1;
	private Role r2;

	public RolePair(Role r1, Role r2) {
		this.r1 = r1;
		this.r2 = r2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((r1 == null) ? 0 : r1.hashCode());
		result = prime * result + ((r2 == null) ? 0 : r2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		RolePair o = (RolePair) obj;
		return (r1 == o.r1 && r2 == o.r2) || (r2 == o.r1 && r1 == o.r2);
	}

}
