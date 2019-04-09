package com.paladin.credit.service.org.dto;

import com.paladin.framework.common.OffsetPage;
import com.paladin.framework.common.QueryCondition;
import com.paladin.framework.common.QueryType;

public class OrgAgencyQuery extends OffsetPage {
	
	private String licenseNo;
	
	private String name;

	@QueryCondition(type=QueryType.LIKE)
	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	@QueryCondition(type=QueryType.LIKE)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}