package com.paladin.credit.service.template.vo;

public class TemplateItemAgencyVO {


	private String id;

	// 项目ID
	private String itemId;

	// 机构ID
	private String agencyId;

	private String agencyName;

	private String agencyItemId;

	private String itemName;

	private String itemTargetType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}


	public String getAgencyItemId() {
		return agencyItemId;
	}

	public void setAgencyItemId(String agencyItemId) {
		this.agencyItemId = agencyItemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemTargetType() {
		return itemTargetType;
	}

	public void setItemTargetType(String itemTargetType) {
		this.itemTargetType = itemTargetType;
	}

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
}