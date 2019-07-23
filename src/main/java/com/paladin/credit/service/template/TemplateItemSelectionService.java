package com.paladin.credit.service.template;

import com.paladin.credit.mapper.template.TemplateItemSelectionMapper;
import com.paladin.credit.model.template.TemplateItemSelection;
import com.paladin.framework.common.Condition;
import com.paladin.framework.common.QueryType;
import com.paladin.framework.core.ServiceSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateItemSelectionService extends ServiceSupport<TemplateItemSelection> {

	@Autowired
	private TemplateItemSelectionMapper  templateItemSelectionMapper;

	public List<TemplateItemSelection> findSelectionByItem(String itemId) {
		return templateItemSelectionMapper.findSelectionByItem(itemId);
	}

	public int removeSelectionByItem(String itemId) {
		return removeByExample(new Condition(TemplateItemSelection.COLUMN_FIELD_ITEM_ID, QueryType.EQUAL, itemId));
	}
}