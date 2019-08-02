package com.paladin.credit.mapper.department;

import com.paladin.credit.model.department.DepartmentPersonCredit;
import com.paladin.framework.core.configuration.mybatis.CustomMapper;
import org.apache.ibatis.annotations.Param;

public interface DepartmentPersonCreditMapper extends CustomMapper<DepartmentPersonCredit> {

    int updateNoReportStatusById(@Param("id") String id);

    int updateHaveReportedStatusById(@Param("id") String id);
}