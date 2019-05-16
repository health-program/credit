package com.paladin.credit.mapper.org;

import com.paladin.credit.model.org.OrgPersonnel;
import com.paladin.credit.service.org.vo.OrgPersonnelVO;
import com.paladin.framework.core.configuration.mybatis.CustomMapper;

import java.util.ArrayList;

public interface OrgPersonnelMapper extends CustomMapper<OrgPersonnel>{

    ArrayList<OrgPersonnelVO> searchName();
}