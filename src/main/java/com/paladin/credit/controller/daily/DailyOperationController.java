package com.paladin.credit.controller.daily;

import com.paladin.credit.model.template.TemplateItem;
import com.paladin.credit.service.template.TemplateItemAgencyService;
import com.paladin.credit.service.template.TemplateItemService;
import com.paladin.credit.service.template.dto.TemplateItemAgencyQuery;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.web.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * <日常操作功能管理>
 *
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
    return "/credit/daily/daily_operation_index.html";
    }


    @RequestMapping(value = "/find/page/{targetType}", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Object findPage(TemplateItemAgencyQuery query,@PathVariable String targetType) {
        int type = Integer.parseInt(targetType);
        if (type == TemplateItem.ITEM_TARGET_TYPE_AGENCY) {
            query.setItemTargetType(type);
        }else if (type == TemplateItem.ITEM_TARGET_TYPE_PERSONNEL ){
            query.setItemTargetType(type);
        } else {
            query.setItemTargetType(type);
        }
    return CommonResponse.getSuccessResponse(
        templateItemAgencyService.searchTemplatesByQuery(query));
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
        return "/credit/daily/daily_operation_write.html";
    }

}
