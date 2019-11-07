package com.paladin.credit.controller.xczf;

import com.paladin.common.core.ConstantsContainer;
import com.paladin.credit.service.supervise.SuperviseRecordService;
import com.paladin.credit.service.supervise.dto.SuperviseRecordQuery;
import com.paladin.credit.service.supervise.vo.SuperviseRecordVO;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.web.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <现在执法非法行医>
 *
 * @author Huangguochen
 * @create 2019/11/6 15:18
 */
@Controller
@RequestMapping("/illegal/practice")
public class IllegalPracticeController extends ControllerSupport {

    @Autowired
    private SuperviseRecordService superviseRecordService;

    @RequestMapping(value = "/find/record", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object findPage(SuperviseRecordQuery query) {
        return CommonResponse.getSuccessResponse(superviseRecordService.searchRecords(query));
    }

    @GetMapping("/get")
    @ResponseBody
    public Object getDetail(@RequestParam String id) {
        return CommonResponse.getSuccessResponse(beanCopy(superviseRecordService.get(id), new SuperviseRecordVO()));
    }

    @GetMapping("/constant/info")
    @ResponseBody
    public Object getInfo() {
        return CommonResponse.getSuccessResponse(ConstantsContainer.getType("wjs-info-entry-type"));
    }

    @GetMapping("/constant/grade")
    @ResponseBody
    public Object getGrade() {
        return CommonResponse.getSuccessResponse(ConstantsContainer.getType("selection-grade-type"));
    }

    @GetMapping("/constant/identification")
    @ResponseBody
    public Object getidentification() {
        return CommonResponse.getSuccessResponse(ConstantsContainer.getType("identification-type"));
    }

    @GetMapping("/constant/punishment")
    @ResponseBody
    public Object getPunishment() {
        return CommonResponse.getSuccessResponse(ConstantsContainer.getType("punishment-type"));
    }

    @GetMapping("/constant/information")
    @ResponseBody
    public Object getInformation() {
        return CommonResponse.getSuccessResponse(ConstantsContainer.getType("information-usage-scope"));
    }

    @GetMapping("/constant/dishonesty")
    @ResponseBody
    public Object getdishonesty() {
        return CommonResponse.getSuccessResponse(ConstantsContainer.getType("dishonesty-degree-type"));
    }


    @GetMapping("/constant/status")
    @ResponseBody
    public Object getsSatus() {
        return CommonResponse.getSuccessResponse(ConstantsContainer.getType("administrative-status-type"));
    }

}
