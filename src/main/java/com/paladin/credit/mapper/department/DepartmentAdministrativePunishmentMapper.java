package com.paladin.credit.mapper.department;

import com.paladin.credit.model.department.DepartmentAdministrativePunishment;
import com.paladin.framework.core.configuration.mybatis.CustomMapper;
import org.apache.ibatis.annotations.Param;

public interface DepartmentAdministrativePunishmentMapper extends CustomMapper<DepartmentAdministrativePunishment> {

    int updateNoReportStatusById(@Param("id") String id);

    int updateHaveReportedStatusById(@Param("id") String id);
}