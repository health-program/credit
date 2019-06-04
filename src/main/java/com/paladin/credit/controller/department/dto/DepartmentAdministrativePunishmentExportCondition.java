package com.paladin.credit.controller.department.dto;

import com.paladin.common.core.export.ExportCondition;
import com.paladin.credit.service.department.dto.DepartmentAdministrativePunishmentQuery;

public class DepartmentAdministrativePunishmentExportCondition extends ExportCondition {

	private DepartmentAdministrativePunishmentQuery query;

	public DepartmentAdministrativePunishmentQuery getQuery() {
		return query;
	}

	public void setQuery(DepartmentAdministrativePunishmentQuery query) {
		this.query = query;
	}

}