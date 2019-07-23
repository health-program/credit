package com.paladin.credit.mapper.template;

import com.paladin.credit.model.template.TemplateItemSelection;
import com.paladin.framework.core.configuration.mybatis.CustomMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TemplateItemSelectionMapper extends CustomMapper<TemplateItemSelection> {

    List<TemplateItemSelection> findSelectionByItem(@Param("itemId") String itemId);
}