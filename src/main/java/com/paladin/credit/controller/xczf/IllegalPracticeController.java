package com.paladin.credit.controller.xczf;

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




}
