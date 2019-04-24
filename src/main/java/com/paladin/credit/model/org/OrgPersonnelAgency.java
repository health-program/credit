package com.paladin.credit.model.org;

import com.paladin.framework.common.BaseModel;
import javax.persistence.Id;

public class OrgPersonnelAgency extends BaseModel {

	// 
	@Id
	private String id;

	// 名称
	private String name;

	// 
	private String agencyId;

	// 账号
	private String account;

	// 角色
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