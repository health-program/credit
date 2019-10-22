package com.paladin.credit.service.org.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class OrgSuperviserDTO {

	//
	private String id;

	// 名称
	@NotBlank(message = "名称不能为空")
	private String name;

	// 监管范围
	private String superviseScope;

	// 账号
	@Size(min = 5,max = 30,message = "账号长度为5~30位")
	private String account;

	// 角色
	@NotBlank(message = "角色不能为空")
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

	public String getSuperviseScope() {
		return superviseScope;
	}

	public void setSuperviseScope(String superviseScope) {
		this.superviseScope = superviseScope;
	}

}