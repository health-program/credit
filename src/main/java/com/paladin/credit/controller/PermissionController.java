package com.paladin.credit.controller;

import com.paladin.common.core.ConstantsContainer;
import com.paladin.common.core.permission.PermissionContainer;
import com.paladin.common.core.permission.Role;
import com.paladin.credit.core.CreditAgencyContainer;
import com.paladin.credit.core.CreditUserSession;
import com.paladin.credit.core.DataPermissionUtil;
import com.paladin.credit.model.org.OrgPersonnel;
import com.paladin.credit.service.org.OrgPersonnelService;
import com.paladin.framework.common.Condition;
import com.paladin.framework.common.QueryType;
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
import java.util.stream.Collectors;

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
		HashMap<String, Object> result = new HashMap<>(2);
		List<CreditAgencyContainer.Agency> agencies = DataPermissionUtil.getManageAgency();
		List<OrgPersonnel> newPersonnels = null;
		if (agencies != null && agencies.size() > 0) {
			List<String> agencyIds = agencies.stream().map(CreditAgencyContainer.Agency::getId).collect(Collectors.toList());
			List<OrgPersonnel> personnels = personnelService.searchAll(new Condition(OrgPersonnel.COLUMN_FIELD_AGENCY_ID, QueryType.IN, agencyIds));
			newPersonnels = personnels.stream().collect(ArrayList::new, (lists, orgPersonnel) -> {
				String newIdentificationNo = "无身份证号";
				String identificationNo = orgPersonnel.getIdentificationNo();
				if (identificationNo != null && identificationNo.length() > 14) {
					newIdentificationNo = identificationNo.substring(0,14) + "****";
				}
				String newName = orgPersonnel.getName() + "(" + newIdentificationNo + ")";
				orgPersonnel.setName(newName);
				lists.add(orgPersonnel);
			}, List::addAll);
		}
		result.put("people",newPersonnels);
		result.put("agency",agencies);
		return CommonResponse.getSuccessResponse(result);
	}

	@RequestMapping("/code/all")
	@ResponseBody
	public Object findAllCode() {
		List<Map<String, Object>> result;
		List<ConstantsContainer.KeyValue> keyValues = ConstantsContainer.getType("supervise-scope");
		result = keyValues.stream().collect(ArrayList::new, (lists, keyValue) -> {
			HashMap<String, Object> map = new HashMap<>(2);
			map.put("id", keyValue.getKey());
			map.put("name", keyValue.getValue());
			lists.add(map);
		}, List::addAll);
		return CommonResponse.getSuccessResponse(result);
	}

}