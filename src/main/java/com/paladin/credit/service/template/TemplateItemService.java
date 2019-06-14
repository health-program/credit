package com.paladin.credit.service.template;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.paladin.credit.mapper.template.TemplateItemMapper;
import com.paladin.credit.model.template.TemplateItem;
import com.paladin.credit.model.template.TemplateItemSelection;
import com.paladin.credit.service.template.dto.TemplateItemDTO;
import com.paladin.credit.service.template.dto.TemplateItemQuery;
import com.paladin.credit.service.template.dto.TemplateItemSelectionDTO;
import com.paladin.credit.service.template.vo.TemplateItemDetailVO;
import com.paladin.framework.common.PageResult;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.copy.SimpleBeanCopier.SimpleBeanCopyUtil;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.framework.utils.uuid.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TemplateItemService extends ServiceSupport<TemplateItem> {

	@Autowired
	private TemplateItemSelectionService templateItemSelectionService;

	@Autowired
	private TemplateItemMapper templateItemMapper;

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

	public TemplateItemDetailVO getOrgItem(String id,String agencyId) {
		if (id == null || id.length() == 0) {
			throw new BusinessException("找不到对应模板项目");
		}
		TemplateItem item = get(id);
		if (item == null) {
			throw new BusinessException("找不到对应模板项目");
		}
		TemplateItemDetailVO detail = new TemplateItemDetailVO();
		SimpleBeanCopyUtil.simpleCopy(item, detail);
		List<TemplateItemSelection> selections = templateItemSelectionService.findSelectionByItem(id);
		List<TemplateItemSelection> newSelections;
		newSelections = selections.stream().filter(templateItemSelection -> templateItemSelection.getSelectionGrade() <= 2).collect(Collectors.toList());
		detail.setSelections(newSelections);
		detail.setAgencyId(agencyId);
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

    public PageResult<TemplateItemDetailVO> searchPageTemplatesByGrade(TemplateItemQuery templateItemQuery) {
		Page<TemplateItemDetailVO> page = PageHelper.offsetPage(templateItemQuery.getOffset(), templateItemQuery.getLimit());
		templateItemMapper.searchPageTemplatesByGrade(templateItemQuery);
		return new PageResult<>(page);
	}

}