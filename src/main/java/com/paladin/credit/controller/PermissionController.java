package com.paladin.credit.controller;

import com.paladin.common.core.permission.PermissionContainer;
import com.paladin.common.core.permission.Role;
import com.paladin.credit.core.CreditUserSession;
import com.paladin.credit.core.DataPermissionUtil;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.web.response.CommonResponse;

import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@ApiIgnore
@Controller
@RequestMapping("/credit/permission")
public class PermissionController extends ControllerSupport {

	@Autowired
	private PermissionContainer permissionContainer;

	@RequestMapping("/role/lower")
	@ResponseBody
	public Object findLowerRole() {
		List<Role> roles = permissionContainer.getRoles();
		CreditUserSession userSession = CreditUserSession.getCurrentUserSession();

		List<Map<String, Object>> result = new ArrayList<>(roles.size());
		if (userSession.isAdminRoleLevel()) {
			for (Role role : roles) {
				HashMap<String, Object> map = new HashMap<>();
				map.put("id", role.getId());
				map.put("name", role.getRoleName());
				result.add(map);
			}
		} else if (userSession.getRoleLevel() == CreditUserSession.ROLE_LEVEL_AGENCY) {
			int level = userSession.getRoleLevel();
			for (Role role : roles) {
				if (role.getRoleLevel() < level) {
					HashMap<String, Object> map = new HashMap<>();
					map.put("id", role.getId());
					map.put("name", role.getRoleName());
					result.add(map);
				}
			}
		}

		return CommonResponse.getSuccessResponse(result);
	}

	@RequestMapping("/agency/lower")
	@ResponseBody
	public Object findLowerAgency() {
		return CommonResponse.getSuccessResponse(DataPermissionUtil.getManageAgency());
	}

}