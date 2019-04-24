package com.paladin.credit.core;

import java.util.ArrayList;

import org.apache.shiro.authc.DisabledAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.paladin.common.core.ConstantsContainer;
import com.paladin.common.core.permission.PermissionContainer;
import com.paladin.common.core.permission.Role;
import com.paladin.common.model.syst.SysUser;
import com.paladin.credit.model.org.OrgSuperviser;
import com.paladin.credit.service.org.OrgSuperviserService;
import com.paladin.framework.core.session.UserSession;

@Component
public class CreditUserSessionFactory {

	@Autowired
	private PermissionContainer permissionContainer;

	@Autowired
	private OrgSuperviserService adminService;

	public UserSession createSession(SysUser sysUser) {
		int type = sysUser.getType();
		CreditUserSession userSession = null;

		if (type == SysUser.TYPE_SYS_ADMIN) {
			userSession = new CreditUserSession(sysUser.getId(), "系统管理员", sysUser.getAccount());
			userSession.isSystemAdmin = true;
			userSession.roleLevel = CreditUserSession.ROLE_LEVEL_ADMIN;
			userSession.currentSuperviseScope = ConstantsContainer.getType("supervise-scope").get(0).getValue();
		} else if (type == SysUser.TYPE_SUPERVISE) {
			String userId = sysUser.getUserId();

			OrgSuperviser admin = adminService.get(userId);
			String scope = admin.getSuperviseScope();
			String roleIds = admin.getRole();

			if (roleIds == null || roleIds.length() == 0) {
				throw new DisabledAccountException("账号异常，没有角色");
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

			if (scope != null && scope.length() > 0) {
				userSession.superviseScopes = scope.split(",");
				userSession.currentSuperviseScope = userSession.superviseScopes[0];				
			}

			userSession.roleIds = roles;
			userSession.roleLevel = roleLevel;
		} else if (type == SysUser.TYPE_AGENCY) {
			// 机构
		} else if (type == SysUser.TYPE_PERSONAL) {
			// 个人
		}

		if (userSession == null) {
			throw new DisabledAccountException();
		}

		return userSession;
	}

}
