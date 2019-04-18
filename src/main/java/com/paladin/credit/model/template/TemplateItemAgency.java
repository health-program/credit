package com.paladin.credit.model.template;

import com.paladin.framework.common.BaseModel;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class TemplateItemAgency extends BaseModel {

	public static final String COLUMN_FIELD_ITEM_ID = "itemId";
	public static final String COLUMN_FIELD_AGENCY_ID= "agencyId";


	@Id
	@GeneratedValue(generator = "UUID")
	private String id;

	// 项目ID
	private String itemId;

	// 机构ID
	private String agencyId;

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

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

}