package com.paladin.credit.controller.org;

import com.paladin.common.core.export.ExportUtil;
import com.paladin.credit.controller.org.dto.OrgRuleManagementExportCondition;
import com.paladin.credit.model.org.OrgRuleManagement;
import com.paladin.credit.service.org.OrgRuleManagementService;
import com.paladin.credit.service.org.dto.OrgRuleManagementDTO;
import com.paladin.credit.service.org.dto.OrgRuleManagementQuery;
import com.paladin.credit.service.org.vo.OrgRuleManagementVO;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.core.query.QueryInputMethod;
import com.paladin.framework.core.query.QueryOutputMethod;
import com.paladin.framework.excel.write.ExcelWriteException;
import com.paladin.framework.utils.uuid.UUIDUtil;
import com.paladin.framework.web.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/credit/org/rule/management")
public class OrgRuleManagementController extends ControllerSupport {

    @Autowired
    private OrgRuleManagementService orgRuleManagementService;

    @GetMapping("/index/{type}")
    @QueryInputMethod(queryClass = OrgRuleManagementQuery.class)
    public String index( @PathVariable String type , Model model) {
        model.addAttribute("type",type);
    return "/credit/org/org_rule_management_index";
    }

    @RequestMapping(value = "/find/page", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    @QueryOutputMethod(queryClass = OrgRuleManagementQuery.class, paramIndex = 0)
    public Object findPage(OrgRuleManagementQuery query) {
        return CommonResponse.getSuccessResponse(orgRuleManagementService.searchPage(query));
    }
    
    @GetMapping("/get")
    @ResponseBody
    public Object getDetail(@RequestParam String id, Model model) {   	
        return CommonResponse.getSuccessResponse(beanCopy(orgRuleManagementService.get(id), new OrgRuleManagementVO()));
    }
    
    @GetMapping("/add/{type}")
    public String addInput(@PathVariable String type, Model model) {
        model.addAttribute("type",type);
        return "/credit/org/org_rule_management_add";
    }

    @GetMapping("/detail")
    public String detailInput(@RequestParam String id,@RequestParam String type, Model model) {
    	model.addAttribute("id", id);
    	model.addAttribute("type", type);
        return "/credit/org/org_rule_management_detail";
    }
    
    @PostMapping("/save")
	@ResponseBody
    public Object save(@Valid OrgRuleManagementDTO orgRuleManagementDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return validErrorHandler(bindingResult);
		}
        OrgRuleManagement model = beanCopy(orgRuleManagementDTO, new OrgRuleManagement());
		String id = UUIDUtil.createUUID();
		model.setId(id);
		if (orgRuleManagementService.save(model) > 0) {
			return CommonResponse.getSuccessResponse(beanCopy(orgRuleManagementService.get(id), new OrgRuleManagementVO()));
		}
		return CommonResponse.getFailResponse();
	}

    @PostMapping("/update")
	@ResponseBody
    public Object update(@Valid OrgRuleManagementDTO orgRuleManagementDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return validErrorHandler(bindingResult);
		}
		String id = orgRuleManagementDTO.getId();
		OrgRuleManagement model = beanCopy(orgRuleManagementDTO, orgRuleManagementService.get(id));
		if (orgRuleManagementService.update(model) > 0) {
			return CommonResponse.getSuccessResponse(beanCopy(orgRuleManagementService.get(id), new OrgRuleManagementVO()));
		}
		return CommonResponse.getFailResponse();
	}

    @RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Object delete(@RequestParam String id) {
        return CommonResponse.getResponse(orgRuleManagementService.removeByPrimaryKey(id));
    }
    
    @PostMapping(value = "/export")
	@ResponseBody
	public Object export(@RequestBody OrgRuleManagementExportCondition condition) {
		if (condition == null) {
			return CommonResponse.getFailResponse("导出失败：请求参数异常");
		}
		condition.sortCellIndex();
		OrgRuleManagementQuery query = condition.getQuery();
		try {
			if (query != null) {
				if (condition.isExportAll()) {
					return CommonResponse.getSuccessResponse("success", ExportUtil.export(condition, orgRuleManagementService.searchAll(query), OrgRuleManagement.class));
				} else if (condition.isExportPage()) {
					return CommonResponse.getSuccessResponse("success",
							ExportUtil.export(condition, orgRuleManagementService.searchPage(query).getData(), OrgRuleManagement.class));
				}
			}
			return CommonResponse.getFailResponse("导出数据失败：请求参数错误");
		} catch (IOException | ExcelWriteException e) {
			return CommonResponse.getFailResponse("导出数据失败：" + e.getMessage());
		}
	}
}