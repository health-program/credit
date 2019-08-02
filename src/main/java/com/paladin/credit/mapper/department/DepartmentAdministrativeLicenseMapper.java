package com.paladin.credit.mapper.department;

import com.paladin.credit.model.department.DepartmentAdministrativeLicense;
import com.paladin.framework.core.configuration.mybatis.CustomMapper;
import org.apache.ibatis.annotations.Param;

public interface DepartmentAdministrativeLicenseMapper extends CustomMapper<DepartmentAdministrativeLicense> {

    int updateNoReportStatusById(@Param("id") String id);

    int updateHaveReportedStatusById(@Param("id") String id);
}