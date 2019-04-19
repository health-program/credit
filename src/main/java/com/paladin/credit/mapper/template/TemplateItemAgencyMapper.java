package com.paladin.credit.mapper.template;

import com.paladin.credit.model.template.TemplateItemAgency;
import com.paladin.credit.service.template.dto.TemplateItemAgencyQuery;
import com.paladin.credit.service.template.vo.TemplateItemAgencyVO;
import com.paladin.framework.core.configuration.mybatis.CustomMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TemplateItemAgencyMapper extends CustomMapper<TemplateItemAgency>{

    List<TemplateItemAgencyVO> searchAgencyTemplatesByQuery(@Param("query") TemplateItemAgencyQuery query);

    List<TemplateItemAgencyVO> searchNoConfigTemplatesByQuery(@Param("query") TemplateItemAgencyQuery query);

    List<TemplateItemAgencyVO> searchTemplatesByQuery(@Param("query") TemplateItemAgencyQuery query);
}