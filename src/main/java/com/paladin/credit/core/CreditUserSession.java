package com.paladin.credit.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paladin.common.core.permission.MenuPermission;
import com.paladin.common.core.permission.PermissionContainer;
import com.paladin.common.core.permission.Role;
import com.paladin.framework.core.session.UserSession;

public class CreditUserSession extends UserSession implements AuthorizationInfo {

	private static final long serialVersionUID = -4164990537271107241L;

	public CreditUserSession(String userId, String userName, String account) {
		super(userId, userName, account);
	}

	/**
	 * 获取当前用户会话
	 * 
	 * @return
	 */
	public static CreditUserSession getCurrentUserSession() {
		return (CreditUserSession) SecurityUtils.getSubject().getPrincipal();
	}

	/** 默认最低级别 */
	public static int ROLE_LEVEL_PERSONAL = 1;
	/** 机构级别 */
	public static int ROLE_LEVEL_AGENCY = 2;
	/** 管理级别 */
	public static int ROLE_LEVEL_ADMIN = 9;

	protected List<String> roleIds;
	protected int roleLevel;
	protected String[] agencyIds;
	protected boolean isSystemAdmin = false;

	/**
	 * 获取角色拥有的数据等级
	 * 
	 * @return
	 */
	public int getRoleLevel() {
		return roleLevel;
	}

	/**
	 * 是否管理系统级别
	 * 
	 * @return
	 */
	public boolean isAdminRoleLevel() {
		return ROLE_LEVEL_ADMIN == roleLevel || isSystemAdmin;
	}

	/**
	 * 获取所属机构ID
	 * 
	 * @return
	 */
	public String[] getAgencyIds() {
		return agencyIds;
	}

	/**
	 * 菜单资源
	 * 
	 * @return
	 */
	public Collection<MenuPermission> getMenuResources() {
		PermissionContainer container = PermissionContainer.getInstance();
		if (isSystemAdmin) {
			return container.getSystemAdminRole().getMenuPermissions();
		}

		if (roleIds.size() == 1) {
			return container.getRole(roleIds.get(0)).getMenuPermissions();
		}

		ArrayList<Role> roles = new ArrayList<>(roleIds.size());
		for (String rid : roleIds) {
			Role role = container.getRole(rid);
			if (role != null) {
				roles.add(role);
			}
		}
		return Role.getMultiRoleMenuPermission(roles);
	}

	@Override
	@JsonIgnore
	public Collection<String> getRoles() {
		return roleIds;
	}

	@Override
	@JsonIgnore
	public Collection<String> getStringPermissions() {
		return null;
	}

	@Override
	@JsonIgnore
	public Collection<Permission> getObjectPermissions() {
		PermissionContainer container = PermissionContainer.getInstance();
		if (isSystemAdmin) {
			return container.getSystemAdminRole().getPermissionObjects();
		}

		if (roleIds.size() == 1) {
			return container.getRole(roleIds.get(0)).getPermissionObjects();
		}

		ArrayList<Role> roles = new ArrayList<>(roleIds.size());
		for (String rid : roleIds) {
			Role role = container.getRole(rid);
			if (role != null) {
				roles.add(role);
			}
		}
		return Role.getMultiRolePermissionObject(roles);
	}

	@Override
	@JsonIgnore
	public Object getUserForView() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("username", getUserName());
		map.put("account", getAccount());
		return map;
	}

}
