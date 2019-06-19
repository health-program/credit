package com.paladin.credit.mapper.org;

import com.paladin.credit.model.org.OrgPersonnel;
import com.paladin.credit.service.org.dto.OrgPersonnelQuery;
import com.paladin.credit.service.org.vo.OrgPersonnelVO;
import com.paladin.framework.core.configuration.mybatis.CustomMapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface OrgPersonnelMapper extends CustomMapper<OrgPersonnel> {

    ArrayList<OrgPersonnelVO> searchName();

    List<OrgPersonnelVO> searchPeoplePage(@Param("query") OrgPersonnelQuery query);
}