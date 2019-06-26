package com.paladin.credit.controller.template;

import com.paladin.common.core.export.ExportUtil;
import com.paladin.credit.controller.template.dto.TemplateItemAgencyExportCondition;
import com.paladin.credit.core.CreditUserSession;
import com.paladin.credit.model.template.TemplateItemAgency;
import com.paladin.credit.service.org.dto.OrgAgencyQuery;
import com.paladin.credit.service.template.TemplateItemAgencyService;
import com.paladin.credit.service.template.dto.TemplateItemAgencyQuery;
import com.paladin.credit.service.template.vo.TemplateItemAgencyVO;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.core.query.QueryInputMethod;
import com.paladin.framework.excel.write.ExcelWriteException;
import com.paladin.framework.web.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/credit/template/item/agency")
public class TemplateItemAgencyController extends ControllerSupport {

    @Autowired
    private TemplateItemAgencyService templateItemAgencyService;

	@GetMapping("/index")
	@QueryInputMethod(queryClass = OrgAgencyQuery.class)
	public String index(Model model) {
		CreditUserSession userSession = CreditUserSession.getCurrentUserSession();
		int roleLevel = userSession.getRoleLevel();
		if (roleLevel >= CreditUserSession.ROLE_LEVEL_SUPERVISE) {
			model.addAttribute("code",userSession.getCurrentSuperviseScope());
		}
		return "/credit/template/template_agencies";
	}

    @GetMapping("/get")
    @ResponseBody
    public Object getDetail(@RequestParam String id, Model model) {   	
        return CommonResponse.getSuccessResponse(beanCopy(templateItemAgencyService.get(id), new TemplateItemAgencyVO()));
    }

	@GetMapping("/configuration/index")
	public String configurationIndex(@RequestParam Integer code,@RequestParam String name,Model model) {
		model.addAttribute("code",code);
		model.addAttribute("name",name);
		return "/credit/template/template_item_agency_index";
	}

	@RequestMapping(value = "/find/page", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object findPage(TemplateItemAgencyQuery query) {
		return CommonResponse.getSuccessResponse(templateItemAgencyService.searchConfigedTemplatesByQuery(query));
	}
    
    @GetMapping("/configuration/add/index")
	@QueryInputMethod(queryClass = TemplateItemAgencyQuery.class)
    public String addIndex( @RequestParam String name,@RequestParam Integer code, Model model) {
		model.addAttribute("code",code);
		model.addAttribute("name",name);
        return "/credit/template/template_item_agency_config";
    }


	@RequestMapping(value = "/configuration/find", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object findTemplates(TemplateItemAgencyQuery query) {
		return CommonResponse.getSuccessResponse(templateItemAgencyService.searchNoConfigTemplatesByQuery(query));
	}


  @GetMapping(value = "/config")
  @ResponseBody
  public Object config(@RequestParam Integer code, @RequestParam String itemId) {
		return CommonResponse.getResponse(templateItemAgencyService.templateConfigById(code,itemId));
  }


	@PostMapping(value = "/configs")
	@ResponseBody
	public Object configs(@RequestParam Integer code, @RequestParam(name = "itemId[]")List<String> itemIds) {
		return CommonResponse.getResponse(templateItemAgencyService.templateConfigByIds(code,itemIds));
	}


    @GetMapping("/detail")
    public String detailInput(@RequestParam String itemId, Model model) {
    	model.addAttribute("itemId", itemId);
        return "/credit/template/template_item_agency_detail";
    }

    @RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Object delete(@RequestParam String id) {
        return CommonResponse.getResponse(templateItemAgencyService.removeByPrimaryKey(id));
    }
    
    @PostMapping(value = "/export")
	@ResponseBody
	public Object export(@RequestBody TemplateItemAgencyExportCondition condition) {
		if (condition == null) {
			return CommonResponse.getFailResponse("导出失败：请求参数异常");
		}
		condition.sortCellIndex();
		TemplateItemAgencyQuery query = condition.getQuery();
		try {
			if (query != null) {
				if (condition.isExportAll()) {
					return CommonResponse.getSuccessResponse("success", ExportUtil.export(condition, templateItemAgencyService.searchAll(query), TemplateItemAgency.class));
				} else if (condition.isExportPage()) {
					return CommonResponse.getSuccessResponse("success",
							ExportUtil.export(condition, templateItemAgencyService.searchPage(query).getData(), TemplateItemAgency.class));
				}
			}
			return CommonResponse.getFailResponse("导出数据失败：请求参数错误");
		} catch (IOException | ExcelWriteException e) {
			return CommonResponse.getFailResponse("导出数据失败：" + e.getMessage());
		}
	}
}