package com.paladin.credit.controller.template;

import com.paladin.credit.controller.template.dto.TemplateItemExportCondition;
import com.paladin.credit.model.template.TemplateItem;
import com.paladin.credit.service.template.TemplateItemService;
import com.paladin.credit.service.template.dto.TemplateItemQuery;
import com.paladin.credit.service.template.dto.TemplateItemDTO;

import com.paladin.common.core.export.ExportUtil;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.core.query.QueryInputMethod;
import com.paladin.framework.core.query.QueryOutputMethod;
import com.paladin.framework.excel.write.ExcelWriteException;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.framework.utils.uuid.UUIDUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

import javax.validation.Valid;

@Controller
@RequestMapping("/credit/template/item")
public class TemplateItemController extends ControllerSupport {

    @Autowired
    private TemplateItemService templateItemService;

    @GetMapping("/index")
    @QueryInputMethod(queryClass = TemplateItemQuery.class)
    public String index() {
        return "/credit/template/template_item_index";
    }

    @RequestMapping(value = "/find/page", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    @QueryOutputMethod(queryClass = TemplateItemQuery.class, paramIndex = 0)
    public Object findPage(TemplateItemQuery query) {
        return CommonResponse.getSuccessResponse(templateItemService.searchPage(query));
    }
    
    @GetMapping("/get")
    @ResponseBody
    public Object getDetail(@RequestParam String id, Model model) {   	
        return CommonResponse.getSuccessResponse(templateItemService.getItem(id));
    }
    
    @GetMapping("/add")
    public String addInput() {
        return "/credit/template/template_item_add";
    }

    @GetMapping("/detail")
    public String detailInput(@RequestParam String id, Model model) {
    	model.addAttribute("id", id);
        return "/credit/template/template_item_detail";
    }
    
    @PostMapping("/save")
	@ResponseBody
    public Object save(@Valid @RequestBody TemplateItemDTO templateItemDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return validErrorHandler(bindingResult);
		}
		String id = UUIDUtil.createUUID();
		templateItemDTO.setId(id);
		if (templateItemService.saveItem(templateItemDTO)) {
			return CommonResponse.getSuccessResponse(templateItemService.getItem(id));
		}
		return CommonResponse.getFailResponse();
	}

    @PostMapping("/update")
	@ResponseBody
    public Object update(@Valid @RequestBody TemplateItemDTO templateItemDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return validErrorHandler(bindingResult);
		}
		String id = templateItemDTO.getId();
		if (templateItemService.updateItem(templateItemDTO)) {
			return CommonResponse.getSuccessResponse(templateItemService.getItem(id));
		}
		return CommonResponse.getFailResponse();
	}

    @RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Object delete(@RequestParam String id) {
        return CommonResponse.getResponse(templateItemService.removeItem(id));
    }
    
    @PostMapping(value = "/export")
	@ResponseBody
	public Object export(@RequestBody TemplateItemExportCondition condition) {
		if (condition == null) {
			return CommonResponse.getFailResponse("导出失败：请求参数异常");
		}
		condition.sortCellIndex();
		TemplateItemQuery query = condition.getQuery();
		try {
			if (query != null) {
				if (condition.isExportAll()) {
					return CommonResponse.getSuccessResponse("success", ExportUtil.export(condition, templateItemService.searchAll(query), TemplateItem.class));
				} else if (condition.isExportPage()) {
					return CommonResponse.getSuccessResponse("success",
							ExportUtil.export(condition, templateItemService.searchPage(query).getData(), TemplateItem.class));
				}
			}
			return CommonResponse.getFailResponse("导出数据失败：请求参数错误");
		} catch (IOException | ExcelWriteException e) {
			return CommonResponse.getFailResponse("导出数据失败：" + e.getMessage());
		}
	}
}