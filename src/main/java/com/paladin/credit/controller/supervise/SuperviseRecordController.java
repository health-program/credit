package com.paladin.credit.controller.supervise;

import com.paladin.common.core.export.ExportUtil;
import com.paladin.credit.controller.supervise.dto.SuperviseRecordExportCondition;
import com.paladin.credit.model.supervise.SuperviseRecord;
import com.paladin.credit.service.supervise.SuperviseRecordService;
import com.paladin.credit.service.supervise.dto.SuperviseRecordDTO;
import com.paladin.credit.service.supervise.dto.SuperviseRecordQuery;
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

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/credit/supervise/record")
public class SuperviseRecordController extends ControllerSupport {

    @Autowired
    private SuperviseRecordService superviseRecordService;

    @GetMapping("/index")
    @QueryInputMethod(queryClass = SuperviseRecordQuery.class)
    public String index() {
        return "/credit/supervise/supervise_record_index";
    }

    @RequestMapping(value = "/find/page", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    @QueryOutputMethod(queryClass = SuperviseRecordQuery.class, paramIndex = 0)
    public Object findPage(SuperviseRecordQuery query) {
        return CommonResponse.getSuccessResponse(superviseRecordService.searchPage(query));
    }

    @GetMapping("/get")
    @ResponseBody
    public Object getDetail(@RequestParam String id, Model model) {
        return CommonResponse.getSuccessResponse(beanCopy(superviseRecordService.get(id), new SuperviseRecordVO()));
    }

    @GetMapping("/add")
    public String addInput() {
        return "/credit/supervise/supervise_record_add";
    }

    @GetMapping("/detail")
    public String detailInput(@RequestParam String id, Model model) {
        model.addAttribute("id", id);
        return "/credit/supervise/supervise_record_detail";
    }

    @PostMapping("/save")
    @ResponseBody
    public Object save(
            @Valid @RequestBody SuperviseRecordDTO superviseRecordDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return validErrorHandler(bindingResult);
        }

        return CommonResponse.getResponse(superviseRecordService.saveRecords(superviseRecordDTO));
    }

    @GetMapping("/report/org/index")
    public String reportOrgIndex() {
        return "/credit/supervise/supervise_record_report_org_index";
    }

    @RequestMapping(value = "/find/report/org/page", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object findReportOrgPage(SuperviseRecordQuery query) {
        return CommonResponse.getSuccessResponse(superviseRecordService.searchAgencyReportsOrgByQuery(query));
    }

    @GetMapping("/report/index")
    public String reportIndex() {
        return "/credit/supervise/supervise_record_report_index";
    }

    @RequestMapping(value = "/find/report/page", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object findReportPage(SuperviseRecordQuery query) {
        return CommonResponse.getSuccessResponse(superviseRecordService.searchAgencyReportsByQuery(query));
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

/*    @PostMapping("/update")
	@ResponseBody
    public Object update(@Valid SuperviseRecordDTO superviseRecordDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return validErrorHandler(bindingResult);
		}
		String id = superviseRecordDTO.getId();
		SuperviseRecord model = beanCopy(superviseRecordDTO, superviseRecordService.get(id));
		if (superviseRecordService.update(model) > 0) {
			return CommonResponse.getSuccessResponse(beanCopy(superviseRecordService.get(id), new SuperviseRecordVO()));
		}
		return CommonResponse.getFailResponse();
	}*/

    @RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object delete(@RequestParam String id) {
        return CommonResponse.getResponse(superviseRecordService.removeByPrimaryKey(id));
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
                    return CommonResponse.getSuccessResponse("success", ExportUtil.export(condition, superviseRecordService.searchAll(query), SuperviseRecord.class));
                } else if (condition.isExportPage()) {
                    return CommonResponse.getSuccessResponse("success",
                            ExportUtil.export(condition, superviseRecordService.searchPage(query).getData(), SuperviseRecord.class));
                }
            }
            return CommonResponse.getFailResponse("导出数据失败：请求参数错误");
        } catch (IOException | ExcelWriteException e) {
            return CommonResponse.getFailResponse("导出数据失败：" + e.getMessage());
        }
    }
}