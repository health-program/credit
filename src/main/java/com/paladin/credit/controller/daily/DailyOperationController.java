package com.paladin.credit.controller.daily;

import com.paladin.credit.core.CreditAgencyContainer;
import com.paladin.credit.core.CreditUserSession;
import com.paladin.credit.model.template.TemplateItem;
import com.paladin.credit.service.template.TemplateItemAgencyService;
import com.paladin.credit.service.template.TemplateItemService;
import com.paladin.credit.service.template.dto.TemplateItemAgencyQuery;
import com.paladin.credit.service.template.dto.TemplateItemQuery;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.web.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <日常操作功能管理>
 * @author Huangguochen
 * @create 2019/4/17 13:09
 */
@Controller
@RequestMapping("/credit/daily/operation")
public class DailyOperationController extends ControllerSupport {

    @Autowired
    private TemplateItemAgencyService templateItemAgencyService;
    @Autowired
    private TemplateItemService templateItemService;


    @GetMapping("/{targetType}")
    public String index( @PathVariable String targetType,Model model) {
        int type = Integer.parseInt(targetType);
        if (type == TemplateItem.ITEM_TARGET_TYPE_AGENCY) {
            model.addAttribute("name","医疗机构");
            model.addAttribute("type","1");
        }else if ( type == TemplateItem.ITEM_TARGET_TYPE_PERSONNEL){
            model.addAttribute("name","医疗人员");
            model.addAttribute("type","2");
        } else {
            model.addAttribute("name","医疗相关人员");
            model.addAttribute("type","3");
        }
    return "/credit/daily/daily_operation_index";
    }


    @RequestMapping(value = "/find/page/{targetType}", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Object findPage(TemplateItemAgencyQuery query,@PathVariable String targetType) {
        query.setItemTargetType(Integer.valueOf(targetType));
        String code = CreditUserSession.getCurrentUserSession().getCurrentSuperviseScope();
        query.setCode(Integer.valueOf(code));
        return CommonResponse.getSuccessResponse(templateItemAgencyService.searchTemplatesByQuery(query));
    }

    @GetMapping("/write/{targetType}")
    public String write(@PathVariable String targetType,@RequestParam String itemId, Model model) {
        int type = Integer.parseInt(targetType);
        if (type == TemplateItem.ITEM_TARGET_TYPE_AGENCY) {
            model.addAttribute("name","医疗机构");
            model.addAttribute("url","/credit/daily/operation/1");
        }else if ( type == TemplateItem.ITEM_TARGET_TYPE_PERSONNEL){
            model.addAttribute("name","医疗人员");
            model.addAttribute("url","/credit/daily/operation/2");
        } else {
            model.addAttribute("name","医疗相关人员");
            model.addAttribute("url","/credit/daily/operation/3");
        }
        model.addAttribute("itemId", itemId);
        return "/credit/daily/daily_operation_write";
    }

    @GetMapping("/wjs/{targetType}")
    public String wjsIndex( @PathVariable String targetType,Model model) {
        model.addAttribute("type",targetType);
        CreditUserSession userSession = CreditUserSession.getCurrentUserSession();
        model.addAttribute("isWjs",1);
        model.addAttribute("code",userSession.getCurrentSuperviseScope());
        model.addAttribute("roleLevel",userSession.getRoleLevel());
        return "/credit/supervise/supervise_record_wjs_index";
    }

    @GetMapping("/org")
    public String orgIndex(Model model) {
        String agencyId = "";
        String agencyName = "";
        CreditUserSession userSession = CreditUserSession.getCurrentUserSession();
        String[] agencyIds = userSession.getAgencyIds();
        List<CreditAgencyContainer.Agency> agencies = CreditAgencyContainer.getAgencies(agencyIds);
        if (agencies != null) {
            CreditAgencyContainer.Agency agency = agencies.get(0);
            agencyId = agency.getId();
            agencyName = agency.getName();
        }
        model.addAttribute("itemTargetType",1);
        model.addAttribute("agencyName",agencyName);
        model.addAttribute("agencyId",agencyId);
        return "/credit/daily/daily_operation_org_index";
    }

    @RequestMapping(value = "/find/page/org", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Object findPageOrg(TemplateItemQuery templateItemQuery) {
        return CommonResponse.getSuccessResponse(templateItemService.searchPageTemplatesByGrade(templateItemQuery));
    }

    @GetMapping("/write/org")
    public String writeOrg(@RequestParam String itemId,@RequestParam String agencyName,@RequestParam String agencyId, Model model) {
        model.addAttribute("itemId", itemId);
        model.addAttribute("agencyName", agencyName);
        model.addAttribute("agencyId", agencyId);
        return "/credit/daily/daily_operation_org_write";
    }

}
