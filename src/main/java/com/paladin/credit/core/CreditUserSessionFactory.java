package com.paladin.credit.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.DisabledAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.paladin.common.core.permission.PermissionContainer;
import com.paladin.common.core.permission.Role;
import com.paladin.common.model.syst.SysUser;
import com.paladin.credit.core.CreditAgencyContainer.Agency;
import com.paladin.credit.model.org.OrgAdmin;
import com.paladin.credit.service.org.OrgAdminService;
import com.paladin.framework.core.session.UserSession;

@Component
public class CreditUserSessionFactory {

	@Autowired
	private PermissionContainer permissionContainer;

	@Autowired
	private OrgAdminService adminService;

	public UserSession createSession(SysUser sysUser) {
		int type = sysUser.getType();
		CreditUserSession userSession = null;

		if (type == SysUser.TYPE_SYS_ADMIN) {
			userSession = new CreditUserSession(sysUser.getId(), "系统管理员", sysUser.getAccount());
			userSession.isSystemAdmin = true;
			userSession.roleLevel = CreditUserSession.ROLE_LEVEL_ADMIN;
		} else if (type == SysUser.TYPE_AGENCY_ADMIN) {
			String userId = sysUser.getUserId();

			OrgAdmin admin = adminService.get(userId);
			String agencyIds = admin.getManageAgency();
			String roleIds = admin.getRole();

			if (agencyIds == null || agencyIds.length() == 0) {
				throw new DisabledAccountException("账号异常，没有可管理的机构");
			}

			if (roleIds == null || roleIds.length() == 0) {
				throw new DisabledAccountException("账号异常，没有角色");
			}

			String[] aids = agencyIds.split(",");
			// check agency;
			List<Agency> agencies = CreditAgencyContainer.getAgencies(aids);
			if (agencies == null || agencies.size() == 0) {
				throw new DisabledAccountException("账号异常，没有可管理的机构");
			}

			String[] rids = roleIds.split(",");

			ArrayList<String> roles = new ArrayList<>(rids.length);
			int roleLevel = -1;
			for (String rid : rids) {
				Role role = permissionContainer.getRole(rid);
				if (role != null) {
					roles.add(rid);
					roleLevel = Math.max(roleLevel, role.getRoleLevel());
				}
			}

			if (roles.size() == 0) {
				throw new DisabledAccountException("账号异常，没有角色");
			}

			userSession = new CreditUserSession(userId, admin.getName(), admin.getAccount());

			userSession.agencyIds = new String[agencies.size()];
			for (int i = 0; i < agencies.size(); i++) {
				userSession.agencyIds[i] = agencies.get(i).getId();
			}

			userSession.roleIds = roles;
			userSession.roleLevel = roleLevel;
		} else if (type == SysUser.TYPE_PERSONAL) {

		}

		if (userSession == null) {
			throw new DisabledAccountException();
		}

		return userSession;
	}

}
