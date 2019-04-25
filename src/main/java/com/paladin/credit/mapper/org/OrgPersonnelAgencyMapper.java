package com.paladin.credit.mapper.org;

import com.paladin.credit.model.org.OrgPersonnelAgency;
import com.paladin.credit.service.org.dto.OrgPersonnelAgencyQuery;
import com.paladin.credit.service.org.vo.OrgPersonnelAgencyVO;
import com.paladin.framework.core.configuration.mybatis.CustomMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrgPersonnelAgencyMapper extends CustomMapper<OrgPersonnelAgency>{

    List<OrgPersonnelAgencyVO> findPageList(@Param("query") OrgPersonnelAgencyQuery query);
}