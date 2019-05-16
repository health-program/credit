package com.paladin.credit.controller;

import com.paladin.common.core.permission.PermissionContainer;
import com.paladin.common.core.permission.Role;
import com.paladin.credit.core.CreditUserSession;
import com.paladin.credit.core.DataPermissionUtil;
import com.paladin.credit.service.org.OrgPersonnelService;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.web.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiIgnore
@Controller
@RequestMapping("/credit/permission")
public class PermissionController extends ControllerSupport {

	@Autowired
	private PermissionContainer permissionContainer;
	@Autowired
	private OrgPersonnelService personnelService;

	@RequestMapping("/role/lower")
	@ResponseBody
	public Object findLowerRole() {
		List<Role> roles = permissionContainer.getRoles();
		CreditUserSession userSession = CreditUserSession.getCurrentUserSession();

		List<Map<String, Object>> result = new ArrayList<>(roles.size());
		if (userSession.isAdminRoleLevel()) {
			result = roles.stream()
					.filter(role -> role.getRoleLevel() >= CreditUserSession.ROLE_LEVEL_SUPERVISE )
					.collect(ArrayList::new, (lists, role) -> {
						HashMap<String, Object> map = new HashMap<>(2);
						map.put("id", role.getId());
						map.put("name", role.getRoleName());
						lists.add(map);
					}, List::addAll);
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

	@RequestMapping("/role/in")
	@ResponseBody
	public Object findInRole() {
		List<Role> roles = permissionContainer.getRoles();
		CreditUserSession userSession = CreditUserSession.getCurrentUserSession();
		List<Map<String, Object>> result = null;
		if (userSession.isAdminRoleLevel()) {
			result = roles.stream()
					.filter(role -> role.getRoleLevel() == CreditUserSession.ROLE_LEVEL_AGENCY || role.getRoleLevel() == CreditUserSession.ROLE_LEVEL_ADMIN)
					.collect(ArrayList::new, (lists, role) -> {
						HashMap<String, Object> map = new HashMap<>(2);
						map.put("id", role.getId());
						map.put("name", role.getRoleName());
						lists.add(map);
					}, List::addAll);
		}else if (userSession.getRoleLevel() == CreditUserSession.ROLE_LEVEL_AGENCY){
			result = roles.stream()
					.filter(role -> role.getRoleLevel() == CreditUserSession.ROLE_LEVEL_AGENCY)
					.collect(ArrayList::new, (lists, role) -> {
						HashMap<String, Object> map = new HashMap<>(2);
						map.put("id", role.getId());
						map.put("name", role.getRoleName());
						lists.add(map);
					}, List::addAll);
		}

		return CommonResponse.getSuccessResponse(result);
	}

	@RequestMapping("/agency/lower")
	@ResponseBody
	public Object findLowerAgency() {
		return CommonResponse.getSuccessResponse(DataPermissionUtil.getManageAgency());
	}

	@RequestMapping("/people/lower")
	@ResponseBody
	public Object findLowerPeople() {
		return CommonResponse.getSuccessResponse(personnelService.searchName());
	}

}