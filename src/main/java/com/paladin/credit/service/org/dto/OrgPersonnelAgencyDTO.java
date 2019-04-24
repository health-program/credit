package com.paladin.credit.service.org.dto;

import javax.validation.constraints.NotEmpty;

public class OrgPersonnelAgencyDTO {


	private String id;


	@NotEmpty(message = "名称不能为空")
	private String name;

	private String agencyId;

	@NotEmpty(message = "账号不能为空")
	private String account;

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

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
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