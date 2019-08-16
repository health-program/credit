package com.paladin.credit.controller.org;

import com.paladin.common.core.export.ExportUtil;
import com.paladin.credit.controller.org.dto.OrgPunishmentDiscretionExportCondition;
import com.paladin.credit.model.org.OrgPunishmentDiscretion;
import com.paladin.credit.service.org.OrgPunishmentDiscretionService;
import com.paladin.credit.service.org.dto.OrgPunishmentDiscretionDTO;
import com.paladin.credit.service.org.dto.OrgPunishmentDiscretionQuery;
import com.paladin.credit.service.org.vo.OrgPunishmentDiscretionSimpleVO;
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
@RequestMapping("/credit/org/punishment/discretion")
public class OrgPunishmentDiscretionController extends ControllerSupport {

    @Autowired
    private OrgPunishmentDiscretionService orgPunishmentDiscretionService;

    @GetMapping("/index")
    @QueryInputMethod(queryClass = OrgPunishmentDiscretionQuery.class)
    public String index() {
        return "/credit/org/org_punishment_discretion_index";
    }

    @RequestMapping(value = "/find/page", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    @QueryOutputMethod(queryClass = OrgPunishmentDiscretionQuery.class, paramIndex = 0)
    public Object findPage(OrgPunishmentDiscretionQuery query) {
        return CommonResponse.getSuccessResponse(orgPunishmentDiscretionService.searchPage(query));
    }
    
    @GetMapping("/get")
    @ResponseBody
    public Object getDetail(@RequestParam String id, Model model) {   	
        return CommonResponse.getSuccessResponse(beanCopy(orgPunishmentDiscretionService.get(id), new OrgPunishmentDiscretionSimpleVO()));
    }
    
    @GetMapping("/add")
    public String addInput() {
        return "/credit/org/org_punishment_discretion_add";
    }

    @GetMapping("/detail")
    public String detailInput(@RequestParam String id, Model model) {
    	model.addAttribute("id", id);
        return "/credit/org/org_punishment_discretion_detail";
    }
    
    @PostMapping("/save")
	@ResponseBody
    public Object save(@Valid OrgPunishmentDiscretionDTO orgPunishmentDiscretionDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return validErrorHandler(bindingResult);
		}
        OrgPunishmentDiscretion model = beanCopy(orgPunishmentDiscretionDTO, new OrgPunishmentDiscretion());
		String id = UUIDUtil.createUUID();
		model.setId(id);
		if (orgPunishmentDiscretionService.save(model) > 0) {
			return CommonResponse.getSuccessResponse(beanCopy(orgPunishmentDiscretionService.get(id), new OrgPunishmentDiscretionSimpleVO()));
		}
		return CommonResponse.getFailResponse();
	}

    @PostMapping("/update")
	@ResponseBody
    public Object update(@Valid OrgPunishmentDiscretionDTO orgPunishmentDiscretionDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return validErrorHandler(bindingResult);
		}
		String id = orgPunishmentDiscretionDTO.getId();
		OrgPunishmentDiscretion model = beanCopy(orgPunishmentDiscretionDTO, orgPunishmentDiscretionService.get(id));
		if (orgPunishmentDiscretionService.update(model) > 0) {
			return CommonResponse.getSuccessResponse(beanCopy(orgPunishmentDiscretionService.get(id), new OrgPunishmentDiscretionSimpleVO()));
		}
		return CommonResponse.getFailResponse();
	}

    @RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Object delete(@RequestParam String id) {
        return CommonResponse.getResponse(orgPunishmentDiscretionService.removeByPrimaryKey(id));
    }
    
    @PostMapping(value = "/export")
	@ResponseBody
	public Object export(@RequestBody OrgPunishmentDiscretionExportCondition condition) {
		if (condition == null) {
			return CommonResponse.getFailResponse("导出失败：请求参数异常");
		}
		condition.sortCellIndex();
		OrgPunishmentDiscretionQuery query = condition.getQuery();
		try {
			if (query != null) {
				if (condition.isExportAll()) {
					return CommonResponse.getSuccessResponse("success", ExportUtil.export(condition, orgPunishmentDiscretionService.searchAll(query), OrgPunishmentDiscretion.class));
				} else if (condition.isExportPage()) {
					return CommonResponse.getSuccessResponse("success",
							ExportUtil.export(condition, orgPunishmentDiscretionService.searchPage(query).getData(), OrgPunishmentDiscretion.class));
				}
			}
			return CommonResponse.getFailResponse("导出数据失败：请求参数错误");
		} catch (IOException | ExcelWriteException e) {
			return CommonResponse.getFailResponse("导出数据失败：" + e.getMessage());
		}
	}
}