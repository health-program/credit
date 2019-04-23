package com.paladin.credit.model.template;

import com.paladin.framework.common.BaseModel;

import javax.persistence.Id;

public class TemplateItemAgency extends BaseModel {

	public static final String COLUMN_FIELD_ITEM_ID = "itemId";
	public static final String COLUMN_FIELD_CODE= "code";


	@Id
	private String id;

	private Integer code;

	private String agencyId;

	// 项目ID
	private String itemId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
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