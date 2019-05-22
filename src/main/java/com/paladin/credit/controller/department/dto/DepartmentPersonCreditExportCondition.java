package com.paladin.credit.controller.department.dto;

import com.paladin.common.core.export.ExportCondition;
import com.paladin.credit.service.department.dto.DepartmentPersonCreditQuery;

public class DepartmentPersonCreditExportCondition extends ExportCondition {

	private DepartmentPersonCreditQuery query;

	public DepartmentPersonCreditQuery getQuery() {
		return query;
	}

	public void setQuery(DepartmentPersonCreditQuery query) {
		this.query = query;
	}

}