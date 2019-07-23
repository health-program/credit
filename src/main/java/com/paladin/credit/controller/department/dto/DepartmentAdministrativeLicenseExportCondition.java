package com.paladin.credit.controller.department.dto;

import com.paladin.common.core.export.ExportCondition;
import com.paladin.credit.service.department.dto.DepartmentAdministrativeLicenseQuery;

public class DepartmentAdministrativeLicenseExportCondition extends ExportCondition {

	private DepartmentAdministrativeLicenseQuery query;

	public DepartmentAdministrativeLicenseQuery getQuery() {
		return query;
	}

	public void setQuery(DepartmentAdministrativeLicenseQuery query) {
		this.query = query;
	}

}