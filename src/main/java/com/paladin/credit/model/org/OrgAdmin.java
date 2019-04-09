package com.paladin.credit.model.org;

import com.paladin.framework.common.BaseModel;
import javax.persistence.Id;

public class OrgAdmin extends BaseModel {

	public final static String COLUMN_FIELD_MANAGE_AGENCY = "manageAgency";
	public final static String COLUMN_FIELD_ROLE = "role";

	// 
	@Id
	private String id;

	// 名称
	private String name;

	// 管理机构
	private String manageAgency;

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