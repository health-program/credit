package com.paladin.credit.mapper.template;

import com.paladin.credit.model.template.TemplateItem;
import com.paladin.credit.service.template.dto.TemplateItemQuery;
import com.paladin.credit.service.template.vo.TemplateItemDetailVO;
import com.paladin.framework.core.configuration.mybatis.CustomMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TemplateItemMapper extends CustomMapper<TemplateItem> {

    List<TemplateItemDetailVO> searchPageTemplatesByGrade(@Param("templateItemQuery") TemplateItemQuery templateItemQuery);
}