package com.paladin.credit.controller.supervise;

import com.paladin.common.core.export.ExportUtil;
import com.paladin.common.model.syst.SysAttachment;
import com.paladin.common.service.syst.SysAttachmentService;
import com.paladin.credit.controller.supervise.dto.SuperviseRecordExportCondition;
import com.paladin.credit.core.CreditUserSession;
import com.paladin.credit.service.supervise.SuperviseRecordService;
import com.paladin.credit.service.supervise.dto.SuperviseRecordDTO;
import com.paladin.credit.service.supervise.dto.SuperviseRecordQuery;
import com.paladin.credit.service.supervise.vo.SuperviseRecordReportOrgVO;
import com.paladin.credit.service.supervise.vo.SuperviseRecordReportVO;
import com.paladin.credit.service.supervise.vo.SuperviseRecordSimpleVO;
import com.paladin.credit.service.supervise.vo.SuperviseRecordVO;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.core.query.QueryInputMethod;
import com.paladin.framework.core.query.QueryOutputMethod;
import com.paladin.framework.excel.write.ExcelWriteException;
import com.paladin.framework.web.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/credit/supervise/record")
public class SuperviseRecordController extends ControllerSupport {

    @Autowired
    private SuperviseRecordService superviseRecordService;

    @Autowired
    private SysAttachmentService sysAttachmentService;

    @GetMapping("/index/{type}")
    @QueryInputMethod(queryClass = SuperviseRecordQuery.class)
    public String index( @PathVariable String type, Model model) {
        CreditUserSession userSession = CreditUserSession.getCurrentUserSession();
        model.addAttribute("type",type);
        model.addAttribute("roleLevel",userSession.getRoleLevel());
        model.addAttribute("code",userSession.getCurrentSuperviseScope());
        return "/credit/supervise/supervise_record_index";
    }

    @GetMapping("/org/index")
    public String orgIndex(@RequestParam String agencyName,@RequestParam String agencyId, Model model) {
        model.addAttribute("targetType",1);
        model.addAttribute("agencyName", agencyName);
        model.addAttribute("agencyId", agencyId);
        return "/credit/supervise/supervise_record_org_index";
    }

    @RequestMapping(value = "/find/page", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    @QueryOutputMethod(queryClass = SuperviseRecordQuery.class, paramIndex = 0)
    public Object findPage(SuperviseRecordQuery query) {
        return CommonResponse.getSuccessResponse(superviseRecordService.searchSuperviseRecordsPageByQuery(query));
    }

    @GetMapping("/get")
    @ResponseBody
    public Object getDetail(@RequestParam String id, Model model) {
        return CommonResponse.getSuccessResponse(beanCopy(superviseRecordService.get(id), new SuperviseRecordVO()));
    }

    @GetMapping("/check")
    public String check(@RequestParam String id,@RequestParam String targetType, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("targetType", targetType);
        return "/credit/supervise/supervise_record_check";
    }

    @GetMapping("/wjs/check")
    public String wjsCheck(@RequestParam String id,@RequestParam String targetType, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("targetType", targetType);
        return "/credit/supervise/supervise_record_wjs_check";
    }

    @PostMapping("/check/success")
    @ResponseBody
    public Object checkSuccess(@RequestParam String id, @RequestParam(required = false) String illustrate) {
        return CommonResponse.getResponse(superviseRecordService.check(id, illustrate, true));
    }

    @PostMapping("/check/fail")
    @ResponseBody
    public Object checkFail(@RequestParam String id, @RequestParam String illustrate) {
        return CommonResponse.getResponse(superviseRecordService.check(id, illustrate, false));
    }

    @GetMapping("/grade")
    public String grade(@RequestParam String id,@RequestParam String targetType, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("targetType", targetType);
        return "/credit/supervise/supervise_record_grade";
    }

    @PostMapping("/grade/save")
    @ResponseBody
    public Object gradeSave(@RequestParam String id, Integer resultGrade) {
        return CommonResponse.getResponse(superviseRecordService.grade(id, resultGrade));
    }


    @GetMapping("/detail")
    public String detailInput(@RequestParam String id,@RequestParam String targetType,@RequestParam String name, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("name", name);
        model.addAttribute("targetType", targetType);
        return "/credit/supervise/supervise_record_detail";
    }

    @GetMapping("/wjs/detail")
    public String detailInput(@RequestParam String id,@RequestParam String targetType,Model model) {
        model.addAttribute("id", id);
        model.addAttribute("targetType", targetType);
        return "/credit/supervise/supervise_record_wjs_detail";
    }

    @GetMapping("/org/detail")
    public String detailOrg(@RequestParam String itemId,@RequestParam String agencyName,@RequestParam String agencyId,Model model) {
        model.addAttribute("id", itemId);
        model.addAttribute("agencyName", agencyName);
        model.addAttribute("agencyId", agencyId);
        return "/credit/supervise/supervise_record_org_detail";
    }

    @PostMapping("/save")
    @ResponseBody
    public Object save(@RequestBody SuperviseRecordDTO superviseRecordDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return validErrorHandler(bindingResult);
        }
        return CommonResponse.getResponse(superviseRecordService.saveRecords(superviseRecordDTO));
    }

    @PostMapping("/org/save")
    @ResponseBody
    public Object orgSave(@RequestBody SuperviseRecordDTO superviseRecordDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return validErrorHandler(bindingResult);
        }
        return CommonResponse.getResponse(superviseRecordService.saveOrgRecords(superviseRecordDTO));
    }

    @PostMapping("/wjs/save")
    @ResponseBody
    public Object wjsSave(@Valid SuperviseRecordDTO superviseRecordDTO, BindingResult bindingResult,@RequestParam(required = false) MultipartFile[] scoreAttachmentFiles,@RequestParam(required = false) MultipartFile[] punishAttachmentFiles) {
        if (bindingResult.hasErrors()) {
            return validErrorHandler(bindingResult);
        }
        List<SysAttachment> scoreAttachments = sysAttachmentService.checkOrCreateAttachment(superviseRecordDTO.getScoreAttachment(), scoreAttachmentFiles);
        if (scoreAttachments != null && scoreAttachments.size() > 4) {
            return CommonResponse.getErrorResponse("记分附件数量不能超过4张");
        }
        List<SysAttachment> punishAttachments = sysAttachmentService.checkOrCreateAttachment(superviseRecordDTO.getPunishAttachment(), punishAttachmentFiles);
        if (punishAttachments != null && punishAttachments.size() > 4) {
            return CommonResponse.getErrorResponse("处罚附件数量不能超过4张");
        }
        superviseRecordDTO.setScoreAttachment(sysAttachmentService.splicingAttachmentId(scoreAttachments));
        superviseRecordDTO.setPunishAttachment(sysAttachmentService.splicingAttachmentId(punishAttachments));
        return CommonResponse.getResponse(superviseRecordService.saveWjsRecords(superviseRecordDTO));
    }

    @PostMapping("/wjs/json/save")
    @ResponseBody
    public Object wjsJsonSave(@Valid @RequestBody SuperviseRecordDTO superviseRecordDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return validErrorHandler(bindingResult);
        }
        return CommonResponse.getResponse(superviseRecordService.saveWjsRecords(superviseRecordDTO));
    }

    @GetMapping("/report/org/index")
    public String reportOrgIndex() {
        return "/credit/supervise/supervise_record_report_org_index";
    }

    @RequestMapping(value = "/find/report/org/page", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object findReportOrgPage(SuperviseRecordQuery query) {
        return CommonResponse.getSuccessResponse(superviseRecordService.searchAgencyReportsOrgPageByQuery(query));
    }

    @GetMapping("/report/index")
    public String reportIndex() {
        return "/credit/supervise/supervise_record_report_index";
    }

    @RequestMapping(value = "/find/report/page", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object findReportPage(SuperviseRecordQuery query) {
        return CommonResponse.getSuccessResponse(superviseRecordService.searchAgencyReportsPageByQuery(query));
    }

    @GetMapping("/report/view/{agencyId}/{grade}")
    public String reportIndex(
            @PathVariable String agencyId, @PathVariable String grade, Model model) {
        model.addAttribute("agencyId", agencyId);
        model.addAttribute("grade", grade);
        return "/credit/supervise/supervise_record_report_detail";
    }

    @RequestMapping(value = "/find/report/detail/page", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object findReportDetailPage(@RequestParam String agencyId, @RequestParam Integer grade) {
        return CommonResponse.getSuccessResponse(
                superviseRecordService.searchReportDetailByQuery(agencyId, grade));
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object delete(@RequestParam String id) {
        return CommonResponse.getResponse(superviseRecordService.removeByPrimaryKey(id));
    }


    @RequestMapping(value = "/reply", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object reply(@RequestParam String id) {
        return CommonResponse.getResponse(superviseRecordService.repealSuperviseRecordById(id));
    }


    @GetMapping("/wjs/org/input")
    public String reportOrgInput(Model model) {
        model.addAttribute("type", 1);
        return "/credit/supervise/supervise_record_wjs_org_input";
    }

    @GetMapping("/wjs/people/input")
    public String reportPeopleInput(Model model) {
        model.addAttribute("type", 2);
        return "/credit/supervise/supervise_record_wjs_people_input";
    }

    @GetMapping("/wjs/rpeople/input")
    public String reportRpeopleInput(Model model) {
        model.addAttribute("type", 3);
        return "/credit/supervise/supervise_record_wjs_rpeople_input";
    }

    @PostMapping(value = "/export")
    @ResponseBody
    public Object export(@RequestBody SuperviseRecordExportCondition condition) {
        if (condition == null) {
            return CommonResponse.getFailResponse("导出失败：请求参数异常");
        }
        condition.sortCellIndex();
        SuperviseRecordQuery query = condition.getQuery();
        try {
            if (query != null) {
                if (condition.isExportAll()) {
                    return CommonResponse.getSuccessResponse("success", ExportUtil.export(condition, superviseRecordService.searchSuperviseAllRecordsByQuery(query), SuperviseRecordSimpleVO.class));
                } else if (condition.isExportPage()) {
                    return CommonResponse.getSuccessResponse("success",
                            ExportUtil.export(condition, superviseRecordService.searchSuperviseRecordsPageByQuery(query).getData(), SuperviseRecordSimpleVO.class));
                }
            }
            return CommonResponse.getFailResponse("导出数据失败：请求参数错误");
        } catch (IOException | ExcelWriteException e) {
            return CommonResponse.getFailResponse("导出数据失败：" + e.getMessage());
        }
    }

    @PostMapping(value = "/summary/export")
    @ResponseBody
    public Object summaryExport(@RequestBody SuperviseRecordExportCondition condition) {
        if (condition == null) {
            return CommonResponse.getFailResponse("导出失败：请求参数异常");
        }
        condition.sortCellIndex();
        SuperviseRecordQuery query = condition.getQuery();
        try {
            if (query != null) {
                if (condition.isExportAll()) {
                    return CommonResponse.getSuccessResponse("success", ExportUtil.export(condition, superviseRecordService.searchAllAgencyReportsByQuery(query), SuperviseRecordReportVO.class));
                } else if (condition.isExportPage()) {
                    return CommonResponse.getSuccessResponse("success",
                            ExportUtil.export(condition, superviseRecordService.searchAgencyReportsPageByQuery(query).getData(), SuperviseRecordReportVO.class));
                }
            }
            return CommonResponse.getFailResponse("导出数据失败：请求参数错误");
        } catch (IOException | ExcelWriteException e) {
            return CommonResponse.getFailResponse("导出数据失败：" + e.getMessage());
        }
    }

    @PostMapping(value = "/org/export")
    @ResponseBody
    public Object orgExport(@RequestBody SuperviseRecordExportCondition condition) {
        if (condition == null) {
            return CommonResponse.getFailResponse("导出失败：请求参数异常");
        }
        condition.sortCellIndex();
        SuperviseRecordQuery query = condition.getQuery();
        try {
            if (query != null) {
                if (condition.isExportAll()) {
                    return CommonResponse.getSuccessResponse("success", ExportUtil.export(condition, superviseRecordService.searchAllAgencyReportsOrgByQuery(query), SuperviseRecordReportOrgVO.class));
                } else if (condition.isExportPage()) {
                    return CommonResponse.getSuccessResponse("success",
                            ExportUtil.export(condition, superviseRecordService.searchAgencyReportsOrgPageByQuery(query).getData(), SuperviseRecordReportOrgVO.class));
                }
            }
            return CommonResponse.getFailResponse("导出数据失败：请求参数错误");
        } catch (IOException | ExcelWriteException e) {
            return CommonResponse.getFailResponse("导出数据失败：" + e.getMessage());
        }
    }
}