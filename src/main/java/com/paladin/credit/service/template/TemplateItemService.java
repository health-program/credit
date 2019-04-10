package com.paladin.credit.service.template;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paladin.credit.model.template.TemplateItem;
import com.paladin.credit.model.template.TemplateItemSelection;
import com.paladin.credit.service.template.dto.TemplateItemDTO;
import com.paladin.credit.service.template.dto.TemplateItemSelectionDTO;
import com.paladin.credit.service.template.vo.TemplateItemDetailVO;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.copy.SimpleBeanCopier.SimpleBeanCopyUtil;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.framework.utils.uuid.UUIDUtil;

@Service
public class TemplateItemService extends ServiceSupport<TemplateItem> {

	@Autowired
	private TemplateItemSelectionService templateItemSelectionService;

	public TemplateItemDetailVO getItem(String id) {
		if (id == null || id.length() == 0) {
			throw new BusinessException("找不到对应模板项目");
		}

		TemplateItem item = get(id);
		if (item == null) {
			throw new BusinessException("找不到对应模板项目");
		}

		TemplateItemDetailVO detail = new TemplateItemDetailVO();
		SimpleBeanCopyUtil.simpleCopy(item, detail);
		detail.setSelections(templateItemSelectionService.findSelectionByItem(id));

		return detail;
	}

	@Transactional
	public boolean saveItem(TemplateItemDTO itemDTO) {
		String itemId = itemDTO.getId();
		if (itemId == null || itemId.length() == 0) {
			itemId = UUIDUtil.createUUID();
			itemDTO.setId(itemId);
		}

		TemplateItem item = new TemplateItem();
		SimpleBeanCopyUtil.simpleCopy(itemDTO, item);

		List<TemplateItemSelectionDTO> selections = itemDTO.getSelections();
		if (selections == null || selections.size() == 0) {
			throw new BusinessException("项目选项不能为空");
		}

		for (TemplateItemSelectionDTO selectionDTO : selections) {
			TemplateItemSelection selection = new TemplateItemSelection();
			SimpleBeanCopyUtil.simpleCopy(selectionDTO, selection);
			selection.setItemId(itemId);
			templateItemSelectionService.save(selection);
		}

		return save(item) > 0;
	}

	@Transactional
	public boolean updateItem(TemplateItemDTO itemDTO) {
		String itemId = itemDTO.getId();
		if (itemId == null || itemId.length() == 0) {
			throw new BusinessException("没有对应需要更新的项目");
		}

		TemplateItem item = get(itemId);
		if (item == null) {
			throw new BusinessException("没有对应需要更新的项目");
		}

		SimpleBeanCopyUtil.simpleCopy(itemDTO, item);

		List<TemplateItemSelectionDTO> selections = itemDTO.getSelections();
		if (selections == null || selections.size() == 0) {
			throw new BusinessException("项目选项不能为空");
		}

		templateItemSelectionService.removeSelectionByItem(itemId);

		for (TemplateItemSelectionDTO selectionDTO : selections) {
			TemplateItemSelection selection = new TemplateItemSelection();
			SimpleBeanCopyUtil.simpleCopy(selectionDTO, selection);
			selection.setItemId(itemId);
			templateItemSelectionService.save(selection);
		}

		return update(item) > 0;
	}

	@Transactional
	public boolean removeItem(String itemId) {
		templateItemSelectionService.removeSelectionByItem(itemId);
		return removeByPrimaryKey(itemId) > 0;
	}

}