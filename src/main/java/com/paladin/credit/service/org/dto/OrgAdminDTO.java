package com.paladin.credit.service.org.dto;

import javax.validation.constraints.NotEmpty;

public class OrgAdminDTO {

	//
	private String id;

	// 名称
	@NotEmpty(message = "名称不能为空")
	private String name;

	// 管理机构
	private String manageAgency;

	// 账号
	@NotEmpty(message = "账号不能为空")
	private String account;

	// 角色
	@NotEmpty(message = "角色不能为空")
	private String role;

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