package com.paladin.credit.service.template;

import java.util.List;

import org.springframework.stereotype.Service;

import com.paladin.credit.model.template.TemplateItemSelection;
import com.paladin.framework.common.Condition;
import com.paladin.framework.common.QueryType;
import com.paladin.framework.core.ServiceSupport;

@Service
public class TemplateItemSelectionService extends ServiceSupport<TemplateItemSelection> {

	public List<TemplateItemSelection> findSelectionByItem(String itemId) {
		return searchAll(new Condition(TemplateItemSelection.COLUMN_FIELD_ITEM_ID, QueryType.EQUAL, itemId));
	}

	public int removeSelectionByItem(String itemId) {
		return removeByExample(new Condition(TemplateItemSelection.COLUMN_FIELD_ITEM_ID, QueryType.EQUAL, itemId));
	}
}