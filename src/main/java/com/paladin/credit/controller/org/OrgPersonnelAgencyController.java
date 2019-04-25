package com.paladin.credit.controller.org;

import com.paladin.common.core.export.ExportUtil;
import com.paladin.credit.controller.org.dto.OrgPersonnelAgencyExportCondition;
import com.paladin.credit.model.org.OrgPersonnelAgency;
import com.paladin.credit.service.org.OrgPersonnelAgencyService;
import com.paladin.credit.service.org.dto.OrgPersonnelAgencyDTO;
import com.paladin.credit.service.org.dto.OrgPersonnelAgencyQuery;
import com.paladin.credit.service.org.vo.OrgPersonnelAgencyVO;
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
@RequestMapping("/credit/org/personnel/agency")
public class OrgPersonnelAgencyController extends ControllerSupport {

    @Autowired
    private OrgPersonnelAgencyService orgPersonnelAgencyService;

    @GetMapping("/index")
    @QueryInputMethod(queryClass = OrgPersonnelAgencyQuery.class)
    public String index() {
        return "/credit/org/org_personnel_agency_index";
    }

    @RequestMapping(value = "/find/page", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    @QueryOutputMethod(queryClass = OrgPersonnelAgencyQuery.class, paramIndex = 0)
    public Object findPage(OrgPersonnelAgencyQuery query) {
        return CommonResponse.getSuccessResponse(orgPersonnelAgencyService.findPageList(query));
    }

    @GetMapping("/get")
    @ResponseBody
    public Object getDetail(@RequestParam String id, Model model) {
        return CommonResponse.getSuccessResponse(beanCopy(orgPersonnelAgencyService.get(id), new OrgPersonnelAgencyVO()));
    }

    @GetMapping("/add")
    public String addInput() {
        return "/credit/org/org_personnel_agency_add";
    }

    @GetMapping("/detail")
    public String detailInput(@RequestParam String id, Model model) {
        model.addAttribute("id", id);
        return "/credit/org/org_personnel_agency_detail";
    }


    @PostMapping("/save")
    @ResponseBody
    public Object save(@Valid OrgPersonnelAgencyDTO orgPersonnelAgencyDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return validErrorHandler(bindingResult);
        }
        String id = UUIDUtil.createUUID();
        orgPersonnelAgencyDTO.setId(id);
        if (orgPersonnelAgencyService.saveAgenecy(orgPersonnelAgencyDTO) > 0) {
            return CommonResponse.getSuccessResponse(beanCopy(orgPersonnelAgencyService.get(id), new OrgPersonnelAgencyVO()));
        }
        return CommonResponse.getFailResponse();
    }

    @PostMapping("/update")
    @ResponseBody
    public Object update(@Valid OrgPersonnelAgencyDTO orgPersonnelAgencyDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return validErrorHandler(bindingResult);
        }
        String id = orgPersonnelAgencyDTO.getId();
        OrgPersonnelAgency model = beanCopy(orgPersonnelAgencyDTO, orgPersonnelAgencyService.get(id));
        if (orgPersonnelAgencyService.update(model) > 0) {
            return CommonResponse.getSuccessResponse(beanCopy(orgPersonnelAgencyService.get(id), new OrgPersonnelAgencyVO()));
        }
        return CommonResponse.getFailResponse();
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object delete(@RequestParam String id) {
        return CommonResponse.getResponse(orgPersonnelAgencyService.removeByPrimaryKey(id));
    }

    @PostMapping(value = "/export")
    @ResponseBody
    public Object export(@RequestBody OrgPersonnelAgencyExportCondition condition) {
        if (condition == null) {
            return CommonResponse.getFailResponse("导出失败：请求参数异常");
        }
        condition.sortCellIndex();
        OrgPersonnelAgencyQuery query = condition.getQuery();
        try {
            if (query != null) {
                if (condition.isExportAll()) {
                    return CommonResponse.getSuccessResponse("success", ExportUtil.export(condition, orgPersonnelAgencyService.searchAll(query), OrgPersonnelAgency.class));
                } else if (condition.isExportPage()) {
                    return CommonResponse.getSuccessResponse("success",
                            ExportUtil.export(condition, orgPersonnelAgencyService.searchPage(query).getData(), OrgPersonnelAgency.class));
                }
            }
            return CommonResponse.getFailResponse("导出数据失败：请求参数错误");
        } catch (IOException | ExcelWriteException e) {
            return CommonResponse.getFailResponse("导出数据失败：" + e.getMessage());
        }
    }
}