package com.paladin.credit.mapper.department;

import com.paladin.credit.model.department.DepartmentCredit;
import com.paladin.framework.core.configuration.mybatis.CustomMapper;
import org.apache.ibatis.annotations.Param;

public interface DepartmentCreditMapper extends CustomMapper<DepartmentCredit> {

    int updateNoReportStatusById(@Param("id") String id);

    int updateHaveReportedStatusById(@Param("id") String id);
}