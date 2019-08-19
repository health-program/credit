package com.paladin.credit.controller.org;

import com.paladin.common.core.export.ExportUtil;
import com.paladin.credit.controller.org.dto.OrgPunishmentRulesManageExportCondition;
import com.paladin.credit.model.org.OrgPunishmentRulesManage;
import com.paladin.credit.service.org.OrgPunishmentRulesManageService;
import com.paladin.credit.service.org.dto.OrgPunishmentRulesManageDTO;
import com.paladin.credit.service.org.dto.OrgPunishmentRulesManageQuery;
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
@RequestMapping("/credit/org/punishment/rules/manage")
public class OrgPunishmentRulesManageController extends ControllerSupport {

    @Autowired
    private OrgPunishmentRulesManageService orgPunishmentRulesManageService;

    @GetMapping("/index")
    @QueryInputMethod(queryClass = OrgPunishmentRulesManageQuery.class)
    public String index() {
        return "/credit/org/org_punishment_rules_manage_index";
    }

    @RequestMapping(value = "/find/page", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    @QueryOutputMethod(queryClass = OrgPunishmentRulesManageQuery.class, paramIndex = 0)
    public Object findPage(OrgPunishmentRulesManageQuery query) {
        return CommonResponse.getSuccessResponse(orgPunishmentRulesManageService.searchPage(query));
    }
    
    @GetMapping("/get")
    @ResponseBody
    public Object getDetail(@RequestParam String id) {
        return CommonResponse.getSuccessResponse(orgPunishmentRulesManageService.getRule(id));
    }

    @GetMapping("/get/one")
    @ResponseBody
    public Object getSimpleOne(@RequestParam String id) {
        return CommonResponse.getSuccessResponse(orgPunishmentRulesManageService.get(id));
    }
    
    @GetMapping("/add")
    public String addInput() {
        return "/credit/org/org_punishment_rules_manage_add";
    }

    @GetMapping("/detail")
    public String detailInput(@RequestParam String id, Model model) {
    	model.addAttribute("id", id);
        return "/credit/org/org_punishment_rules_manage_detail";
    }
    
    @PostMapping("/save")
	@ResponseBody
    public Object save(@Valid  @RequestBody OrgPunishmentRulesManageDTO orgPunishmentRulesManageDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return validErrorHandler(bindingResult);
		}
		String id = UUIDUtil.createUUID();
		orgPunishmentRulesManageDTO.setId(id);
		return CommonResponse.getSuccessResponse(orgPunishmentRulesManageService.saveRule(orgPunishmentRulesManageDTO));
	}

    @PostMapping("/update")
	@ResponseBody
    public Object update(@Valid @RequestBody OrgPunishmentRulesManageDTO orgPunishmentRulesManageDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return validErrorHandler(bindingResult);
		}
		return CommonResponse.getSuccessResponse(orgPunishmentRulesManageService.updateRule(orgPunishmentRulesManageDTO));
	}

    @RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Object delete(@RequestParam String id) {
        return CommonResponse.getResponse(orgPunishmentRulesManageService.removeRuleById(id));
    }

    @RequestMapping("/lower")
    @ResponseBody
    public Object findLowerRules() {
        return CommonResponse.getSuccessResponse(orgPunishmentRulesManageService.findLowerRules());
    }
    
    @PostMapping(value = "/export")
	@ResponseBody
	public Object export(@RequestBody OrgPunishmentRulesManageExportCondition condition) {
		if (condition == null) {
			return CommonResponse.getFailResponse("导出失败：请求参数异常");
		}
		condition.sortCellIndex();
		OrgPunishmentRulesManageQuery query = condition.getQuery();
		try {
			if (query != null) {
				if (condition.isExportAll()) {
					return CommonResponse.getSuccessResponse("success", ExportUtil.export(condition, orgPunishmentRulesManageService.searchAll(query), OrgPunishmentRulesManage.class));
				} else if (condition.isExportPage()) {
					return CommonResponse.getSuccessResponse("success",
							ExportUtil.export(condition, orgPunishmentRulesManageService.searchPage(query).getData(), OrgPunishmentRulesManage.class));
				}
			}
			return CommonResponse.getFailResponse("导出数据失败：请求参数错误");
		} catch (IOException | ExcelWriteException e) {
			return CommonResponse.getFailResponse("导出数据失败：" + e.getMessage());
		}
	}
}