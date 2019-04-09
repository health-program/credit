package com.paladin.credit.service.org.vo;

import java.util.List;

import com.paladin.credit.core.CreditAgencyContainer;

public class OrgAdminVO {

	//
	private String id;

	// 名称
	private String name;

	// 管理机构
	private String manageAgency;

	// 账号
	private String account;

	// 角色
	private String role;
	
	// for export
	@SuppressWarnings("unused")
	private String manageAgencyName;

	public String getManageAgencyName() {
		if (manageAgency != null && manageAgency.length() > 0) {
			List<String> names = CreditAgencyContainer.getAgencyNames(manageAgency.split(","));
			if (names != null && names.size() > 0) {
				StringBuilder sb = new StringBuilder();
				for (String name : names) {
					sb.append(name).append("，");
				}

				sb.deleteCharAt(sb.length() - 1);
				return sb.toString();
			}
		}
		return null;
	}
	
	public void setManageAgencyName(String manageAgencyName) {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManageAgency() {
		return manageAgency;
	}

	public void setManageAgency(String manageAgency) {
		this.manageAgency = manageAgency;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}



}